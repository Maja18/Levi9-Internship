package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.ImageDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.exceptions.GroupException;
import Internship.SocialNetworking.exceptions.MediaException;
import Internship.SocialNetworking.exceptions.PersonException;
import Internship.SocialNetworking.exceptions.PostException;
import Internship.SocialNetworking.mappers.PostMapper;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.interface_service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final GroupRepository groupRepository;
    private final  PostRepository postRepository;
    private final PersonRepository personRepository;
    private final NotificationServiceImpl notificationService;
    private final PostMapper postMapper;
    private  String uploadDir = "user-photos";

    @Override
    public PostDTO addNewPost(PostDTO postDTO, Person loggedPerson){
        Post post;
        Optional<GroupNW> group = Optional.ofNullable(groupRepository.findByGroupId(postDTO.getGroupId()));
        if (group.isEmpty()){
            post = addPostOutsideGroup(postDTO,loggedPerson);
        }else{
            post = addPostToGroup(postDTO, group, loggedPerson);
            notificationService.addNotificationPost(group.get().getName(),
                    personRepository.findByEmailEquals(loggedPerson.getEmail()));
        }
        throwExceptionIfPostNotValid(post);
        postRepository.save(post);

        return postMapper.postToPostDTO(post);
    }

    private void throwExceptionIfPostNotValid(Post post) {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("/" + uploadDir + "/");
        List<File> files = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(filePath))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    files.add(path.toFile());
                }
            }
            if (post.getImageUrl() != null){
                boolean isImageNameCorrect = files.stream().anyMatch(f -> post.getImageUrl().equals(f.getName()));
                if (!isImageNameCorrect)
                    throw new MediaException("Image url is incorrect!");
            }
            if (post.getVideoUrl() != null){
                boolean isVideoNameCorrect = files.stream().anyMatch(f -> post.getVideoUrl().equals(f.getName()));
                if (!isVideoNameCorrect)
                    throw new MediaException("Video url is incorrect!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Post addPostToGroup(PostDTO postDTO, Optional<GroupNW> group, Person loggedPerson) {
        Post post = new Post();
        if (group.isEmpty())
            throw new GroupException("Group doesn't exist");
        if (group.get().getMembers().stream().anyMatch(member -> member.getPersonId().equals(loggedPerson.getPersonId()))){
            post.setIsPublic(postDTO.getIsPublic());
            post.setGroupId(postDTO.getGroupId());
            LocalDateTime currentDate = LocalDateTime.now();
            post.setCreationDate(currentDate);
            post.setCreatorId(loggedPerson.getPersonId());
            post.setDescription(postDTO.getDescription());
            post.setImageUrl(postDTO.getImageUrl());
            post.setVideoUrl(postDTO.getVideoUrl());
            post.setIsOver(false);
        } else
            throw new GroupException("You are not member of this group!");

        return post;
    }

    public Post addPostOutsideGroup(PostDTO postDTO, Person loggedPerson) {
        Post post = new Post();
        post.setIsPublic(postDTO.getIsPublic());
        LocalDateTime currentDate = LocalDateTime.now();
        post.setCreationDate(currentDate);
        post.setCreatorId(loggedPerson.getPersonId());
        post.setDescription(postDTO.getDescription());
        post.setImageUrl(postDTO.getImageUrl());
        post.setVideoUrl(postDTO.getVideoUrl());
        post.setIsOver(false);

        return post;
    }

    @Override
    public List<PostDTO> getAllUserPosts(Long userId, Person loggedPerson) {
        List<Post> allPosts = postRepository.findByCreatorId(userId);
        List<Post> posts = new ArrayList<>();
        allPosts.stream().forEach(p-> {
            if(p.getCreationDate().isBefore(LocalDateTime.now().minusDays(1)))
                p.setIsOver(true);
            if (p.getGroupId() != null)
                setAllGroupPosts(loggedPerson, posts, p);
            else
                setAllNotGroupPosts(loggedPerson,userId, posts, p);
        });

        return getPostFiles(postMapper.postsToPostDTOs(posts));
    }

    private void setAllNotGroupPosts(Person loggedPerson,Long userId , List<Post> posts, Post p) {
        Person person = personRepository.findByPersonId(userId);
        if (person == null)
            throw new PersonException(userId, "Person with given id doesn't exist");
        List<Person> personFriends = person.getFriends();
        boolean isNullOrEmpty = ObjectUtils.isEmpty(personFriends);
        if (isNullOrEmpty && p.getIsPublic().equals(Boolean.FALSE))
            return;
        if (isNullOrEmpty && p.getIsPublic().equals(Boolean.TRUE))
            posts.add(p);
        else {
            personFriends.stream().forEach(friend -> {
                if (friend.getPersonId().equals(loggedPerson.getPersonId()) && p.getIsOver().equals(Boolean.FALSE))
                    posts.add(p);
                else if (p.getIsPublic().equals(Boolean.TRUE) && !friend.getPersonId().equals(loggedPerson.getPersonId()) && p.getIsOver().equals(Boolean.FALSE))
                    posts.add(p);
            });
        }

        removeBlockedPosts(loggedPerson, posts, p);
    }

    private void setAllGroupPosts(Person loggedPerson,List<Post> posts, Post p) {
        GroupNW group = groupRepository.findByGroupId(p.getGroupId());
        if (group == null)
            throw new GroupException(p.getGroupId(), "Group with given id doesn't exist");
        List<Person> groupMembers = group.getMembers();
        if (p.getIsOver().equals(Boolean.FALSE)) {
            if (group.getIsPublic().equals(Boolean.TRUE))
                posts.add(p);
            else {
                groupMembers.stream().forEach(member -> {
                    if (member.getPersonId().equals(loggedPerson.getPersonId()))
                        posts.add(p);
                });
            }

            removeBlockedPosts(loggedPerson, posts, p);
        }
    }

    private void removeBlockedPosts(Person loggedPerson, List<Post> posts, Post p) {
        List<Person> blockedPersons = p.getBlockedPersons();
        boolean isNullOrEmpty = ObjectUtils.isEmpty(blockedPersons);
        if(!isNullOrEmpty) {
            blockedPersons.stream().forEach(blockedUser -> {
                if (blockedUser.getPersonId().equals(loggedPerson.getPersonId()))
                    posts.remove(p);
            });
        }
    }

    @Override
    public PostInfoDTO hidePost(HidePostDTO hidePostDTO, Long personId) {

        if(validation(hidePostDTO, personId)) {
            Post post = Optional.ofNullable(postRepository.findByPostId(hidePostDTO.getPostId()))
                    .orElseThrow(() -> new PostException("Post ID doesn't exist!"));
            Person blockPerson = Optional.ofNullable(personRepository.findByPersonId(hidePostDTO.getPersonId()))
                    .orElseThrow(() -> new PersonException("Block person ID doesn't exist!"));
            List<Person> blockListPersons = post.getBlockedPersons();
            blockListPersons.add(blockPerson);
            post.setBlockedPersons(blockListPersons);
            postRepository.save(post);

            return postMapper.postToPostInfoDTO(post);
        }

        log.info("The HidePostDTO and the personId didn't pass validation!");
        return null;
    }

    private boolean validation(HidePostDTO hidePostDTO, Long personId) {
        Person person = Optional.ofNullable(personRepository.findByPersonId(personId))
                .orElseThrow(() -> new PersonException("Person ID doesn't exist!"));
        Person blockPerson = Optional.ofNullable(personRepository.findByPersonId(hidePostDTO.getPersonId()))
                .orElseThrow(() -> new PersonException("Block person ID doesn't exist!"));
        Post post = Optional.ofNullable(postRepository.findByPostId(hidePostDTO.getPostId()))
                .orElseThrow(() -> new PostException("Post ID doesn't exist!"));

        if(person != null && blockPerson != null && post != null
                && person.getPersonId().equals(post.getCreatorId())
                && post.getIsOver().equals(Boolean.FALSE)){

            GroupNW groupNW = groupRepository.findByGroupId(post.getGroupId());

            if(!Objects.equals(person.getPersonId(), blockPerson.getPersonId()) && groupNW != null
                    && groupNW.getMembers().stream().anyMatch(m -> m.getPersonId().equals(blockPerson.getPersonId()))){

                return post.getBlockedPersons().stream().noneMatch(b -> b.getPersonId().equals(blockPerson.getPersonId()));

            }else return post.getGroupId() == null
                    && person.getFriends().stream().anyMatch(f -> f.getPersonId().equals(blockPerson.getPersonId()))
                    && post.getBlockedPersons().stream().noneMatch(b -> b.getPersonId().equals(blockPerson.getPersonId()));
        }

        return false;
    }

    @Override
    public List<PostDTO> getAllFriendPosts(Person loggedPerson) {
        List<Post> friendPosts= new ArrayList<>();
        List<Person> personFriends = loggedPerson.getFriends();
        personFriends.stream().forEach(friend -> {
            List<Post> posts = postRepository.findByCreatorId(friend.getPersonId());
            for (Post p: posts) {
                if(p.getCreationDate().isBefore(LocalDateTime.now().minusDays(1)))
                    p.setIsOver(true);
                if (p.getIsOver().equals(Boolean.FALSE))
                    friendPosts.add(p);
                removeBlockedPosts(loggedPerson, friendPosts, p);
            }
        });

        return getPostFiles(postMapper.postsToPostDTOs(friendPosts));
    }

    @Override
    public List<String> getFileNames(List<MultipartFile> multipartFiles) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for(MultipartFile multipartFile: multipartFiles) {
            String multipartFileName = multipartFile.getOriginalFilename();
            if (multipartFileName != null){
                String fileName = StringUtils.cleanPath(multipartFileName.replaceAll("\\s", ""));
                fileNames.add(fileName);
                uploadDir = "user-photos";
                saveFile(uploadDir, fileName, multipartFile);
            }
        }
        return fileNames;
    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public List<PostDTO> getPostFiles(List<PostDTO> posts) {
        List<PostDTO> postsDto = new ArrayList<>();
        if (!posts.isEmpty()) {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath.concat("/" + uploadDir + "/");
            for (PostDTO post : posts) {
                postsDto.add(getPostFile(post, filePath));
            }
        }

        return postsDto;
    }

    public PostDTO getPostFile(PostDTO postDTO, String filePath) {
        List<ImageDTO> images = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        if (postDTO.getImageUrl() != null && postDTO.getVideoUrl() != null) {
            fileNames.add(postDTO.getImageUrl());
            fileNames.add(postDTO.getVideoUrl());
        }
        else if (postDTO.getImageUrl() != null && postDTO.getVideoUrl() == null)
            fileNames.add(postDTO.getImageUrl());
        else if (postDTO.getVideoUrl() != null && postDTO.getImageUrl() == null)
            fileNames.add(postDTO.getVideoUrl());
        fileNames.stream().forEach(fileName ->{
            ImageDTO imageDTO = new ImageDTO();
            List<byte[]> bytes = new ArrayList<>();
            imageDTO.setImageBytes(bytes);
            File in = new File(filePath + "/"+ fileName);
            try {
                bytes.add(IOUtils.toByteArray(new FileInputStream(in)));
                imageDTO.setImageBytes(bytes);
                images.add(imageDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }catch(NullPointerException n) {
                n.printStackTrace();
            }
        });

        postDTO.setImages(images);
        return postDTO;
    }
}

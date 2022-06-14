package Internship.SocialNetworking.Models;


import java.util.List;

public class GroupNW {
        private long GroupId;
        private String Name;
        private String Description;
        private boolean isPublic;
        private long CreatorId;
        private List<Person> ListUsers;

        public GroupNW(){}

        public long getGroupId() {
            return GroupId;
        }

        public void setGroupId(long groupId) {
            GroupId = groupId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public boolean isPublic() {
            return isPublic;
        }

        public void setPublic(boolean aPublic) {
            isPublic = aPublic;
        }

        public long getCreatorId() {
            return CreatorId;
        }

        public void setCreatorId(long creatorId) {
            CreatorId = creatorId;
        }

        public List<Person> getListUsers() {
        return ListUsers;
        }

        public void setListUsers(List<Person> listUsers) {
        ListUsers = listUsers;
        }
}


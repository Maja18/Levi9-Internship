package Internship.SocialNetworking.Models;

import javax.persistence.*;
import java.util.List;

//@Entity
public class GroupNW {
        /*@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)*/
        private long GroupId;
        private String Name;
        private String Description;
        private boolean isPublic;
        private long CreatorId;

       /* @ManyToMany
        @JoinTable(name = "GroupPersons",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "GroupId"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "PersonId"))
        private List<Person> ListUsers;*/

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

        /*public List<Person> getListUsers() {
        return ListUsers;
        }

        public void setListUsers(List<Person> listUsers) {
        ListUsers = listUsers;
        }*/
}


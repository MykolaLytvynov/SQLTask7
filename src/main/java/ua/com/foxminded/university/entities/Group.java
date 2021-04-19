package ua.com.foxminded.university.entities;

public class Group {

    private int id;
    private String group;

    public Group(String group) {
        this.group = group;
    }

    public Group(int id, String group) {
        this.id = id;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", group='" + group + '\'' +
                '}';
    }
}

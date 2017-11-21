package ese.unittests;

public class Student {

    private String name;

    public Student(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.contains("@"))
            throw new IllegalArgumentException("Name contains illegal character.");
        this.name = name;
    }
}

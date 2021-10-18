package resource_pool.demo.domain;

public class table_information {
    private String title;
    private String name;
    private int del;

    public void setDel(int del) {
        this.del = del;
    }

    public int getDel() {
        return del;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}

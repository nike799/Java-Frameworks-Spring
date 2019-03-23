package exodiaapp.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class Document extends BaseEntity {
    private String title;
    private String content;

    public Document() {
    }

    @Column(name = "title",nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "content",length = 10000,columnDefinition = "text",nullable = false)
    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Steven
 */
@Entity
@Table(name = "TBL_BLOGPOST")
@NamedQueries({
    @NamedQuery(name = "BlogPost.findAll",query = "SELECT b FROM BlogPost b")
})
public class BlogPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    private String title;
    
    private String onderwerp;
    @NotNull
    private String content;
    @NotNull
    private String owner;
    
    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name="Blog_id")
    private List<Reaction> reacties;
    
    public BlogPost()
    {
        reacties = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public String getContent() {
        return content;
    }

    public String getOwner() {
        return owner;
    }

    public List<Reaction> getReacties() {
        return reacties;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BlogPost other = (BlogPost) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    public void addReaction(Reaction r)
    {
        reacties.add(r);
    }
    public void removeReaction(Reaction r)
    {
        reacties.remove(r);
    }
    
}

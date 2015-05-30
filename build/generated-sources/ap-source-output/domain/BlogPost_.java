package domain;

import domain.Reaction;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-30T21:20:57")
@StaticMetamodel(BlogPost.class)
public class BlogPost_ { 

    public static volatile SingularAttribute<BlogPost, String> owner;
    public static volatile ListAttribute<BlogPost, Reaction> reacties;
    public static volatile SingularAttribute<BlogPost, String> onderwerp;
    public static volatile SingularAttribute<BlogPost, Integer> id;
    public static volatile SingularAttribute<BlogPost, String> title;
    public static volatile SingularAttribute<BlogPost, String> content;

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import domain.BlogPost;
import domain.Reaction;
import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Steven
 */
@Path("blogs")
@Transactional(dontRollbackOn = {BadRequestException.class, NotFoundException.class})
public class Blogs {
    
    @PersistenceContext
    private EntityManager em;
    
    @Resource
    private Validator validator;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BlogPost> getAllBlogs(@QueryParam("first")@DefaultValue("0") int first,@QueryParam("results")@DefaultValue("10")int results)
    {
        TypedQuery<BlogPost> allBlogs = em.createNamedQuery("BlogPost.findAll", BlogPost.class);
        allBlogs.setFirstResult(first);
        allBlogs.setMaxResults(results);
        return allBlogs.getResultList();
            
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewBlog(BlogPost post)
    {
        Set<ConstraintViolation<BlogPost>>violations = validator.validate(post);
        if(!violations.isEmpty())
        {
            throw new BadRequestException("post is incorrect");
        }
        em.persist(post);
        return Response.created(URI.create("/"+post.getId())).build();
    }
    @Path("{id}")
    @DELETE
    public boolean deleteBlog(@PathParam("id")int id)
    {
        BlogPost post = em.find(BlogPost.class, id);
        if(post==null)
        {
            throw new NotFoundException("Post not found");
        }
        em.remove(post);
        em.flush();
        return true;
    }
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BlogPost getBlog(@PathParam("id")int id)
    {
        BlogPost post = em.find(BlogPost.class, id);
        if(post == null)
        {
            throw new NotFoundException("Post niet gevonden");
        }
        return post;
    }
    
    @Path("{id}/reactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reaction> getReactionsFromPost(@PathParam("id")int id)
    {
        BlogPost post = em.find(BlogPost.class, id);
        if(post == null)
        {
            throw new NotFoundException("Post niet gevonden");
        }
        return post.getReacties();
    }
    
    @Path("{id}/reactions")
    @POST
    @Consumes
    public Response createNewReaction(@PathParam("id")int id,Reaction reaction)
    {
        BlogPost post = em.find(BlogPost.class, id);
         if(post == null)
        {
            throw new NotFoundException("Post niet gevonden");
        }
         post.addReaction(reaction);
         em.persist(post);
         return Response.created(URI.create("/"+reaction.getId())).build();
    }
    
    
}

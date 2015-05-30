/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import domain.BlogPost;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Steven
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class BlogPostReader implements MessageBodyReader<BlogPost>{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return BlogPost.class.isAssignableFrom(type);
    }

    @Override
    public BlogPost readFrom(Class<BlogPost> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        
        try(JsonReader in = Json.createReader(entityStream))
        {
            JsonObject jsonBlog = in.readObject();
            BlogPost post = new BlogPost();
            post.setTitle(jsonBlog.getString("title"));
            post.setContent(jsonBlog.getString("content"));
            post.setOwner(jsonBlog.getString("owner"));
            post.setOnderwerp(jsonBlog.getString("onderwerp",null));
          
            return post;
        }
        catch(JsonException | ClassCastException ex)
        {
            throw new BadRequestException("Fout in jsonReader");
        }
    }
    
    
}

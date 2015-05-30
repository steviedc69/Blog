/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import domain.BlogPost;
import domain.Reaction;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Steven
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class BlogPostWriter implements MessageBodyWriter<BlogPost>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return BlogPost.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(BlogPost t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(BlogPost t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        
        JsonObjectBuilder blogJson = Json.createObjectBuilder();
        blogJson.add("id", t.getId());
        blogJson.add("title", t.getTitle());
        blogJson.add("content", t.getContent());
        blogJson.add("owner", t.getOwner());
        if(t.getOnderwerp() != null)
        {
           blogJson.add("onderwerp", t.getOnderwerp());  
        }
           
        if(t.getReacties()!=null)
        {
            JsonArrayBuilder reactionArray = Json.createArrayBuilder();
            for(Reaction r : t.getReacties())
            {
                reactionArray.add(r.getId());
            }
            blogJson.add("reactions", reactionArray);
        }

        try(JsonWriter out = Json.createWriter(entityStream))
        {
            out.writeObject(blogJson.build());
        }
        
    
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import domain.BlogPost;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
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
public class BlogPostListWriter implements MessageBodyWriter<List<BlogPost>>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

        if(!List.class.isAssignableFrom(type))
        {
            return false;
        }
        
          if (genericType instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
            return arguments.length == 1 && arguments[0].equals(BlogPost.class);
        } else {
            return false;
        }
    }

    @Override
    public long getSize(List<BlogPost> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<BlogPost> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        
        JsonArrayBuilder blogJsonArray = Json.createArrayBuilder();
        
        for(BlogPost b : t)
        {
            JsonObjectBuilder jsonBlog = Json.createObjectBuilder();
            jsonBlog.add("id", b.getId());
            jsonBlog.add("title", b.getTitle());
            jsonBlog.add("owner",b.getOwner());
            if(b.getOnderwerp()!=null)
            {
                jsonBlog.add("onderwerp",b.getOnderwerp());
            }
            blogJsonArray.add(jsonBlog);
                    
        }
        
        try(JsonWriter out = Json.createWriter(entityStream))
        {
            out.writeArray(blogJsonArray.build());
        }
    }
    
    
}

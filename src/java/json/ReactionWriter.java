/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import domain.Reaction;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import javax.json.Json;
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
public class ReactionWriter implements MessageBodyWriter<Reaction>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Reaction.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Reaction t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Reaction t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        JsonObjectBuilder reactionJson = Json.createObjectBuilder();
        reactionJson.add("id", t.getId());
        reactionJson.add("message", t.getMessage());
        reactionJson.add("author", t.getAuthor());
        
        try(JsonWriter out = Json.createWriter(entityStream))
        {
            out.writeObject(reactionJson.build());
        }
    }
    
    
}

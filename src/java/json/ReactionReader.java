/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import domain.Reaction;
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
public class ReactionReader implements MessageBodyReader<Reaction>{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Reaction.class.isAssignableFrom(type);
    }

    @Override
    public Reaction readFrom(Class<Reaction> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try(JsonReader in = Json.createReader(entityStream))
        {
            JsonObject reactionJson = in.readObject();
            Reaction react = new Reaction();
            react.setAuthor(reactionJson.getString("author",null));
            react.setMessage(reactionJson.getString("message",null));
            
            return react;
        }
        catch(JsonException | ClassCastException ex)
        {
            throw new BadRequestException("not able to parse json");
        }
    }
    
}

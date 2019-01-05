package com.madtribe.farcejuggler.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.security.InvalidParameterException;
import java.util.function.Function;

public class JsonBuilder {
    private JsonElement topElement;

    public <K> JsonBuilder array(Function<JsonBuilder.JsonArrayChildren<K>, JsonArrayChildren<K>> func) {
        topElement = new JsonArray();
        func.apply(new JsonArrayChildrenBuilder<K>((JsonArray) topElement));
        return this;
    }

    public JsonElement build() {
        return topElement;
    }

    public JsonBuilder object(Function<JsonBuilder.JsonObjectChild, JsonObjectChild> func) {
        topElement = new JsonObject();
        func.apply(new JsonObjectBuilder((JsonObject) topElement));
        return this;
    }

    public interface JsonObjectChild{
        JsonObjectChild field(String key, String value);

        JsonObjectChild field(String key, Number value);

        JsonObjectChild field(String key, Boolean value);

        <K> JsonObjectChild array(String key, Function<JsonArrayChildren<K>, Void> func);

        JsonObjectChild object(String key, Function<JsonObjectChild, JsonObjectChild> func);
    }

    public static class JsonObjectBuilder implements JsonObjectChild {
        private JsonObject thisObject;

        public JsonObjectBuilder(JsonObject thisObject) {
            this.thisObject = thisObject;
        }

        @Override
        public JsonObjectChild field(String key, String value) {
            thisObject.addProperty(key, value);
            return this;
        }
        @Override
        public JsonObjectChild field(String key, Number value) {
            thisObject.addProperty(key, value);
            return this;
        }
        @Override
        public JsonObjectChild field(String key, Boolean value) {
            thisObject.addProperty(key, value);
            return this;
        }

        @Override
        public <K> JsonObjectChild array(String key, Function<JsonBuilder.JsonArrayChildren<K>, Void> func) {
            JsonArray thisArray = new JsonArray();
            func.apply(new JsonArrayChildrenBuilder<K>((JsonArray) thisArray));
            thisObject.add(key, thisArray);
            return this;
        }

        @Override
        public JsonObjectChild object(String key, Function<JsonBuilder.JsonObjectChild, JsonObjectChild> func) {
            JsonObject topElement = new JsonObject();
            func.apply(new JsonObjectBuilder((JsonObject) topElement));
            thisObject.add(key, topElement);
            return this;
        }
    }

    public static interface JsonArrayChildren<K>{
        JsonArrayChildren add(K val);

        JsonArrayChildrenBuilder<K> addObject();

        JsonArrayChildrenBuilder<K>  addObject(Function<JsonObjectChild, JsonObjectChild> func);
    }

    public static class JsonArrayChildrenBuilder<K> implements JsonArrayChildren<K> {
        private JsonArray thisArray;

        public JsonArrayChildrenBuilder(JsonArray thisArray) {
            this.thisArray = thisArray;
        }

        @Override
        public JsonArrayChildren add(K val) {
            if ( val instanceof String){
                thisArray.add(addNumber((String)val));
            } else if ( val instanceof Number){
                thisArray.add(addNumber((Number)val));
            } else if ( val instanceof Boolean){
                thisArray.add(addBoolean((Boolean)val));
            } else {
                throw new InvalidParameterException(" Can't field " + val.getClass().getName());
            }
            return this;
        }

        @Override
        public JsonArrayChildrenBuilder<K> addObject() {

            thisArray.add(new JsonObject());
            return this;
        }

        @Override
        public JsonArrayChildrenBuilder<K>  addObject(Function<JsonObjectChild, JsonObjectChild> func) {
            JsonObject topElement = new JsonObject();
            func.apply(new JsonObjectBuilder((JsonObject) topElement));
            thisArray.add(topElement);
            return this;
        }

        private JsonPrimitive addBoolean(Boolean val) {
            return new JsonPrimitive(val);
        }

        private JsonPrimitive addNumber(Number val) {
            return new JsonPrimitive(val);
        }

        private JsonPrimitive addNumber(String val) {
            return new JsonPrimitive(val);
        }
    }
}

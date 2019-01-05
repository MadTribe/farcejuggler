package com.madtribe.farcejuggler.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class JsonBuilderTest {
    private JsonBuilder jsonBuilder;

    @Before
    public void setup() {
        jsonBuilder = new JsonBuilder();
    }

    @Test
    public void object() {
        JsonElement element = jsonBuilder.object(x -> {return x;}).build();
        assertThat(element.isJsonObject(), is(true));
    }

    @Test
    public void array() {
        JsonElement element = jsonBuilder.array(builder -> {return builder;}).build();

        assertThat(element.isJsonArray(), is(true));
    }

    @Test
    public void add() {
        JsonElement element;
        element = jsonBuilder.<String>array(builder -> {
            builder.add("cats");
            builder.add("dogs");
            builder.add("bats");
            builder.add("frogs");
            return builder;
        }).build();

        assertThat(element.isJsonArray(), is(true));
        JsonArray array = (JsonArray) element;
        assertThat(array.size(), is(4));
    }

    @Test
    public void object_with_Fields() {
        JsonElement element = jsonBuilder.object(x -> {
            x.field("cat1", "dog1")
            .field("cat2", 3)
            .field("cat3", true)
            .array("cat4", b -> {
                b.add("cats")
                 .add("dogs")
                 .add("bats")
                 .add("frogs");
                return null;
            });
            x.object("monkey", o -> {
                        o.field("red", "green")
                        .array("blue", b -> {
                            b.add(1);
                            return null;
                        });
                        return o;
                    }
            );
            return x;
        }).build();
        assertThat(element.isJsonObject(), is(true));
        String expected = "{\"cat1\":\"dog1\",\"cat2\":3,\"cat3\":true,\"cat4\":[\"cats\",\"dogs\",\"bats\",\"frogs\"],\"monkey\":{\"red\":\"green\",\"blue\":[1]}}";
        assertEquals(expected, element.toString() );

    }

    @Test
    public void array_of_empty_objects(){
        JsonElement element = jsonBuilder.array(x -> {
            x.addObject();
            x.addObject();
            x.addObject();
          return x;
        }).build();
        String expected = "[{},{},{}]";
        assertEquals(expected, element.toString() );
    }

    @Test
    public void array_of_objects(){
        JsonElement element = jsonBuilder.array(x -> {
            x.addObject(b -> {
                b.field("cat", 2);
                b.field("dog", true);
                b.field("hen", "lalala");
                return b;
            });
            x.addObject();
            x.addObject();
            return x;
        }).build();
        String expected = "[{\"cat\":2,\"dog\":true,\"hen\":\"lalala\"},{},{}]";
        assertEquals(expected, element.toString() );
    }

}
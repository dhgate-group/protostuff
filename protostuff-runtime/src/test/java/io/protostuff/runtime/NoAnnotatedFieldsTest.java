package io.protostuff.runtime;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.Tag;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertTrue;

public class NoAnnotatedFieldsTest {



    @Test
    /**
     * 测试无tag
     */
    public void testEntityAnnotated(){
        EntityAnnotated EntityAnnotated = new EntityAnnotated();
        EntityAnnotated.setAlias("Alias");
        EntityAnnotated.setName("Name");
        EntityAnnotated.setId(12);
        byte[] bytes = ProtobufUtil.serialize(EntityAnnotated);
        Object o = ProtobufUtil.deserialize(EntityAnnotated.getClass(),bytes);
        assertTrue(o instanceof EntityAnnotated);
        EntityAnnotated dserEntity = (EntityAnnotated)o;
        assertTrue(dserEntity.getAlias().equals("Alias"));
        assertTrue(dserEntity.getName().equals("Name"));
        assertTrue(dserEntity.getId() == 12);
    }

    @Test
    /**
     * 测试全tag
     */
    public void testEntityFullyAnnotated(){
        EntityFullyAnnotated EntityAnnotated = new EntityFullyAnnotated();
        EntityAnnotated.setAlias("Alias");
        EntityAnnotated.setName("Name");
        EntityAnnotated.setId(12);
        byte[] bytes = ProtobufUtil.serialize(EntityAnnotated);
        Object o = ProtobufUtil.deserialize(EntityAnnotated.getClass(),bytes);
        assertTrue(o instanceof EntityFullyAnnotated);
        EntityFullyAnnotated dserEntity = (EntityFullyAnnotated)o;
        assertTrue(dserEntity.getAlias().equals("Alias"));
        assertTrue(dserEntity.getName().equals("Name"));
        assertTrue(dserEntity.getId() == 12);
    }

    @Test
    /**
     * 测试部分加tag
     */
    public void testEntityPartAnnotated(){
        EntityPartAnnotated EntityAnnotated = new EntityPartAnnotated();
        EntityAnnotated.setAlias("Alias");
        EntityAnnotated.setName("Name");
        EntityAnnotated.setId(12);
        byte[] bytes = ProtobufUtil.serialize(EntityAnnotated);
        Object o = ProtobufUtil.deserialize(EntityAnnotated.getClass(),bytes);
        assertTrue(o instanceof EntityPartAnnotated);
        EntityPartAnnotated dserEntity = (EntityPartAnnotated)o;
        assertTrue(dserEntity.getAlias().equals("Alias"));
        assertTrue(dserEntity.getName().equals("Name"));
        assertTrue(dserEntity.getId() == 12);
    }


    public static class EntityAnnotated
    {
        int id;

        String name;

        String alias;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

    public static class EntityFullyAnnotated
    {

        @Tag(3)
        int id;

        @Tag(5)
        String name;

        @Tag(2)
        String alias;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

    public static class EntityPartAnnotated
    {

        @Tag(3)
        int id;

        @Tag(5)
        String name;

        String alias;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }


    public static class ProtobufUtil{
        /**
         * 线程局部变量
         */
        private static final ThreadLocal<LinkedBuffer> BUFFERS = new ThreadLocal();

        /**
         * 序列化/反序列化包装类 Schema 对象
         */
        private static final Schema<SerializeDeserializeWrapper> WRAPPER_SCHEMA = RuntimeSchema.getSchema(SerializeDeserializeWrapper.class);

        private static final List<String> listBig = new ArrayList();
        private static final List<String> listBigTwo = new ArrayList();

        private static ProtobufUtil instance = new ProtobufUtil();

        public static ProtobufUtil getInstance() {
            return instance;
        }

        /**
         * 序列化对象
         *
         * @param obj 需要序列化的对象
         * @return 序列化后的二进制数组
         */
        @SuppressWarnings("unchecked")
        public static byte[] serialize(Object obj) {
            Class<?> clazz = (Class<?>) obj.getClass();
            LinkedBuffer buffer = null;
            boolean contain = false;
            if(listBigTwo.contains(clazz.getName())){
                contain = true;
                buffer = LinkedBuffer.allocate(2048);
            }else if(listBig.contains(clazz.getName())){
                contain = true;
                buffer = LinkedBuffer.allocate(1024);
            }else {
                buffer = LinkedBuffer.allocate(512);
            }
            try {
                Object serializeObject = obj;
                Schema schema = WRAPPER_SCHEMA;
                if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)
                        || Map.class.isAssignableFrom(clazz) || Set.class.isAssignableFrom(clazz)) {//Protostuff 不支持序列化/反序列化数组、集合等对象,特殊处理
                    serializeObject = SerializeDeserializeWrapper.builder(obj);
                } else {
                    schema = RuntimeSchema.getSchema(clazz);
                }
                byte[] res =  ProtostuffIOUtil.toByteArray(serializeObject, schema, buffer);
                if(!contain){
                    if(res.length>40960){
                        listBigTwo.add(clazz.getName());
                    }else if(res.length>2048){
                        listBig.add(clazz.getName());
                    }
                }

                return res;
            } finally {
                buffer.clear();
                buffer = null;
            }
        }

        /**
         * 反序列化对象
         *
         * @param data 需要反序列化的二进制数组
         * @param clazz 反序列化后的对象class
         * @param <T> 反序列化后的对象类型
         * @return 反序列化后的实例对象
         */
        public static <T> T deserialize(Class<T> clazz, byte[] data) {
            if(clazz==null){
                clazz = (Class<T>) Object[].class;
            }
            if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)
                    || Map.class.isAssignableFrom(clazz) || Set.class.isAssignableFrom(clazz)) {//Protostuff 不支持序列化/反序列化数组、集合等对象,特殊处理
                SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<T>();
                ProtostuffIOUtil.mergeFrom(data, wrapper, WRAPPER_SCHEMA);
                return wrapper.getData();
            } else {
                Schema<T> schema = RuntimeSchema.getSchema(clazz);
                T message = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(data, message, schema);
                return message;
            }
        }
        /**
         * <p>
         * 序列化/反序列化对象包装类 专为基于 Protostuff 进行序列化/反序列化而定义。 Protostuff
         * 是基于POJO进行序列化和反序列化操作。 如果需要进行序列化/反序列化的对象不知道其类型，不能进行序列化/反序列化；
         * 比如Map、List、String、Enum等是不能进行正确的序列化/反序列化。
         * 因此需要映入一个包装类，把这些需要序列化/反序列化的对象放到这个包装类中。 这样每次 Protostuff
         * 都是对这个类进行序列化/反序列化,不会出现不能/不正常的操作出现
         * </p>
         *
         * @author butioy
         */
        static class SerializeDeserializeWrapper<T> {

            private T data;

            public static <T> SerializeDeserializeWrapper<T> builder(T data) {
                SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<T>();
                wrapper.setData(data);
                return wrapper;
            }

            public T getData() {
                return data;
            }

            public void setData(T data) {
                this.data = data;
            }

        }
    }

}

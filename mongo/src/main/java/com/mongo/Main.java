package com.mongo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongo.pojo.Attribute;
import com.mongo.pojo.Category;
import com.mongo.pojo.Product;
import com.mongo.pojo.ProductAttribute;
import com.mongo.pojo.ProductVariant;
import com.mongo.pojo.ProductVariantAttribute;
import com.mongo.rest.HelloServlet;
import com.mongo.rest.ProductRest;

@Configuration
@ComponentScan
public class Main {

    private static ProductRest productRest;
    
    private static List<String> colorList = new ArrayList<>();
    private static List<String> sizeList = new ArrayList<>();
    private static ApplicationContext applicationContext;

    public static void main(String[] args) throws LifecycleException {
        applicationContext = new AnnotationConfigApplicationContext(Main.class);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
        startTomcat();

        
    }

    private static void startTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath(); 
        Context context = tomcat.addContext(contextPath, docBase);
        
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);
        tomcat.setPort(Integer.valueOf(webPort));
        tomcat.addServlet(contextPath, "HelloServlet", applicationContext.getBean(HelloServlet.class));
        context.addServletMappingDecoded("/hello", "HelloServlet");
        tomcat.start();
    }

    private static void populateData() {
        fashionData();
        mobileData();
        earPhones();
        groceryData();
    }

    private static void groceryData() {
        String[] categoryArray = new String[]{"Dal","Rice","Aatta", "Sugar", "Salt", "Cashew", "Almond", "Raisins", "Makhana"};
        String[] brandArray =  new String[]{"Fortune", "Tata", "Ashirwad", "Madhur", "Nutraj", "Premium"};
        String[] quantity = new String[]{"500g", "1Kg", "2kg", "5kg"};
        for(String category : categoryArray) {
            for(String brand : brandArray) {
                List<String> p = new ArrayList<>();
                p.add(brand+"-"+category);
                p.add("description for product "+ brand+"-"+category);
                p.add(category);
                p.add(brand);
                createGroceryProduct(p,quantity);
            }
            
        }
    }

    private static void earPhones() {
        String[] brands = new String[]{"Samsung", "Apple", "Boat", "Mi", "JBL", "OnePlus", "Sony", "Mivi"};
        colorList.clear();
        colorList.add("Red");
        colorList.add("Blue");
        colorList.add("Yellow"); 
        colorList.add("Pink");
        colorList.add("White");
        colorList.add("Black");
        colorList.add("Grey");
        colorList.add("Maroon");
        colorList.add("Orange");
        colorList.add("Green");
        colorList.add("Brown");
        colorList.add("Gold");
        colorList.add("Purple");

        for(String brand : brands) {
            for(int i=1;i<10;i++) {
                List<String> p = new ArrayList<>();
                p.add(brand+"_"+i);
                p.add("description for product "+ brand+"_"+i);
                p.add("Earphones");
                p.add(brand);
                createEarPhoneProduct(p);
            }
            
        }

    }

    private static void mobileData() {
        String[] brands = new String[]{"Samsung", "Apple", "Motorola", "Mi", "Oppo", "Vivo", "Nothing", "Google"};
        colorList.clear();
        colorList.add("Black");
        colorList.add("Blue");
        colorList.add("Silver");
        colorList.add("Green");
        String[] storages = new String[]{"64GB", "128GB", "256GB"};
        String[] rams = new String[]{"4GB", "6GB", "8GB"};

        for(String brand : brands) {
            for(int i=1;i<10;i++) {
                List<String> p = new ArrayList<>();
                p.add(brand+"_"+i);
                p.add("description for product "+ brand+"_"+i);
                p.add("Mobile");
                p.add(brand);
                createMobileProduct(p,storages,rams);
            }
            
        }
    }

    private static void fashionData() {
        String[] categoryArray = new String[]{"Shirt","T-Shirts","Jeans", "Trousers", "Hoody", "Vest", "Underwear", "Top", "Leggies", "Sweater", "Track Pants"};
        String[] brandArray =  new String[]{"Lee","Pepe","Puma","Roadster","Levis","Wrogn","Arrow","Allen Solly","US Polo", "Single", "Spykar", "Killer", "Peter England", "Raymond", "Wrangler", "Indian Terrian", "Mufti", "Van Huesen", "United Color Bentton"};
        
        colorList.add("Red");
        colorList.add("Blue");
        colorList.add("Yellow"); 
        colorList.add("Pink");
        colorList.add("White");
        colorList.add("Black");
        colorList.add("Grey");
        colorList.add("Maroon");
        colorList.add("Orange");
        colorList.add("Green");
        colorList.add("Brown");
        colorList.add("Gold");
        colorList.add("Purple");
        sizeList.add("XS");
        sizeList.add("S");
        sizeList.add("M");
        sizeList.add("L");
        sizeList.add("XL");
        for(String category : categoryArray) {
            for(String brand : brandArray) {
                for(int i=0;i<100;i++) {
                    List<String> p = new ArrayList<>();
                    p.add(brand+"_"+i+"_"+category);
                    p.add("description for product "+ brand+"_"+i+"_"+category);
                    p.add(category);
                    p.add(brand);
                    createProduct(p);
                }
            }
        }
    }

    private static void createProduct(List<String> p) {
        Product product = new Product();
        product.setName(p.get(0));
        product.setDesc(p.get(1));
        product.setBrand(p.get(3));
        product.setPrice(getPrice());
        Category category = new Category();
        category.setName(p.get(2));
        product.setCategory(category);
        ProductAttribute productAttribute = new ProductAttribute();
        List<Attribute> attributes = new ArrayList<>();
        Attribute attribute = new Attribute();
        attribute.setName("Size");
        attribute.setValues(sizeList);
        Attribute attribute1 = new Attribute();
        attribute1.setName("Color");
        List<String> colors = getRandomColors(colorList);
        attribute1.setValues(colors);
        attributes.add(attribute);
        attributes.add(attribute1);
        productAttribute.setAttributes(attributes);
        product.setProductAttribute(productAttribute);
        List<ProductVariant> productVariants = new ArrayList<>();
        for(String color : colors) {
            for(String size : sizeList) {
                System.out.println(color+"-"+size);
                ProductVariant productVariant = new ProductVariant();
                productVariant.setCombination(color+"-"+size);
                productVariant.setPrice(Double.valueOf(getPrice()));
                productVariant.setSku(getRandomString()+"-"+color+"-"+size);
                productVariant.setStock(getStock());
                ProductVariantAttribute productVariantAttribute = new ProductVariantAttribute();
                productVariantAttribute.setAttributeType("Size");
                productVariantAttribute.setAttributeValue(size);
                ProductVariantAttribute productVariantAttribute1 = new ProductVariantAttribute();
                productVariantAttribute1.setAttributeType("Color");
                productVariantAttribute1.setAttributeValue(color);
                productVariant.setVariantAttributes(Arrays.asList(productVariantAttribute,productVariantAttribute1));
                productVariants.add(productVariant);
            }
        }
        
        product.setVariants(productVariants);
        productRest.createProduct(product);
        System.out.println(product.toString());
    }

    private static void createGroceryProduct(List<String> p, String[] quanties) {
        Product product = new Product();
        product.setName(p.get(0));
        product.setDesc(p.get(1));
        product.setBrand(p.get(3));
        product.setPrice(getPrice());
        Category category = new Category();
        category.setName(p.get(2));
        product.setCategory(category);
        ProductAttribute productAttribute = new ProductAttribute();
        List<Attribute> attributes = new ArrayList<>();
        Attribute attribute = new Attribute();
        attribute.setName("Quantity");
        attribute.setValues(Arrays.asList(quanties));
        attributes.add(attribute);
        productAttribute.setAttributes(attributes);
        product.setProductAttribute(productAttribute);
        List<ProductVariant> productVariants = new ArrayList<>();
        for(String quantity : quanties) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setCombination(product.getName()+"-"+quantity);
                productVariant.setPrice(Double.valueOf(getPrice()));
                productVariant.setSku(getRandomString()+"-"+product.getCategory().getName()+"-"+quantity);
                productVariant.setStock(getStock());
                ProductVariantAttribute productVariantAttribute = new ProductVariantAttribute();
                productVariantAttribute.setAttributeType("Quantity");
                productVariantAttribute.setAttributeValue(quantity);
                productVariant.setVariantAttributes(Arrays.asList(productVariantAttribute));
                productVariants.add(productVariant);
            
        }
        
        product.setVariants(productVariants);
        productRest.createProduct(product);
        System.out.println(product.toString());
    }

    private static void createEarPhoneProduct(List<String> p) {
        Product product = new Product();
        product.setName(p.get(0));
        product.setDesc(p.get(1));
        product.setBrand(p.get(3));
        product.setPrice(getPrice());
        Category category = new Category();
        category.setName(p.get(2));
        product.setCategory(category);
        ProductAttribute productAttribute = new ProductAttribute();
        List<Attribute> attributes = new ArrayList<>();
        Attribute attribute1 = new Attribute();
        attribute1.setName("Color");
        List<String> colors = getRandomColors(colorList);
        attribute1.setValues(colors);
        attributes.add(attribute1);
        productAttribute.setAttributes(attributes);
        product.setProductAttribute(productAttribute);
        List<ProductVariant> productVariants = new ArrayList<>();
        for(String color : colors) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setCombination(color);
                productVariant.setPrice(Double.valueOf(getPrice()));
                productVariant.setSku(getRandomString()+"-"+color);
                productVariant.setStock(getStock());
                ProductVariantAttribute productVariantAttribute1 = new ProductVariantAttribute();
                productVariantAttribute1.setAttributeType("Color");
                productVariantAttribute1.setAttributeValue(color);
                productVariant.setVariantAttributes(Arrays.asList(productVariantAttribute1));
                productVariants.add(productVariant);
        }
        product.setVariants(productVariants);
        productRest.createProduct(product);
        System.out.println(product.toString());
    }

    private static void createMobileProduct(List<String> p, String[] storage, String[] rams) {
        Product product = new Product();
        product.setName(p.get(0));
        product.setDesc(p.get(1));
        product.setBrand(p.get(3));
        product.setPrice(getPrice());
        Category category = new Category();
        category.setName(p.get(2));
        product.setCategory(category);
        ProductAttribute productAttribute = new ProductAttribute();
        List<Attribute> attributes = new ArrayList<>();
        Attribute attribute = new Attribute();
        attribute.setName("Storage");
        attribute.setValues(Arrays.asList(storage));
        Attribute attribute1 = new Attribute();
        attribute1.setName("Color");
        attribute1.setValues(colorList);
        attributes.add(attribute);
        attributes.add(attribute1);
        Attribute attribute3 = new Attribute();
        attribute3.setName("Ram");
        attribute3.setValues(Arrays.asList(rams));
        attributes.add(attribute3);
        productAttribute.setAttributes(attributes);
        product.setProductAttribute(productAttribute);
        List<ProductVariant> productVariants = new ArrayList<>();
        for(String color : colorList) {
            for(String ram : rams) {
                for(String s : storage) {
                    ProductVariant productVariant = new ProductVariant();
                    productVariant.setCombination(color+"-"+ram+ "-" + s);
                    productVariant.setPrice(Double.valueOf(getPrice()));
                    productVariant.setSku(getRandomString()+"-" + color+"-"+ram+ "-" + s);
                    productVariant.setStock(getStock());
                    ProductVariantAttribute productVariantAttribute = new ProductVariantAttribute();
                    productVariantAttribute.setAttributeType("Storage");
                    productVariantAttribute.setAttributeValue(s);
                    ProductVariantAttribute productVariantAttribute1 = new ProductVariantAttribute();
                    productVariantAttribute1.setAttributeType("Color");
                    productVariantAttribute1.setAttributeValue(color);
                    ProductVariantAttribute productVariantAttribute2 = new ProductVariantAttribute();
                    productVariantAttribute2.setAttributeType("Ram");
                    productVariantAttribute2.setAttributeValue(ram);
                    productVariant.setVariantAttributes(Arrays.asList(productVariantAttribute,productVariantAttribute1,productVariantAttribute2));
                    productVariants.add(productVariant);
                }
            }
        }
        
        product.setVariants(productVariants);
        productRest.createProduct(product);
        System.out.println(product.toString());
    }

    private static List<String> getRandomColors(List<String> colorList2) {
        Random rand = new Random();
        List<String> colors = new ArrayList<>();
        int low = 0;
        int high = colorList2.size();
        if(!colorList2.isEmpty())
        for(int i=0;i<5;i++) {
            colors.add(colorList2.get(rand.nextInt(high-low)+low));
        }
        return colors;
    }

    private static Integer getPrice() {
        Random r = new Random();
        int low = 100;
        int high = 500;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    private static Integer getStock() {
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    private static String getRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
        return generatedString;
    }
}
title: Testing
speaker: Woody
plugins:
    - echarts

<slide class="bg-black-blue aligncenter" image="https://source.unsplash.com/C1HhAQrbykQ/ .dark">

# Testing {.text-landing.text-shadow}

Woody {.text-intro}

<slide>

# 依赖

1. [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
2. [Spring Test](https://docs.spring.io/spring-framework/docs/5.3.10/reference/html/testing.html#integration-testing) & [Spring Boot Test](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
3. [Mockito](https://site.mockito.org/)
4. [AssertJ](https://assertj.github.io/doc/)，[Hamcrest](http://hamcrest.org/JavaHamcrest/tutorial)，[JSONassert](http://hamcrest.org/JavaHamcrest/tutorial)，[JsonPath](https://github.com/json-path/JsonPath)

<slide>

# JUnit 5

- `@Test` 标识测试方法
- `@ParameterizedTest`、`@ValueSource` 使用指定参数多次运行一个测试方法
- `@BeforeEach`、`@AfterEach`、`@BeforeAll`、`@AfterAll` 标识生命周期方法

<slide>

### @Test

```java
@Test
void addition() {
    assertEquals(2, calculator.add(1, 1));
}
```

<slide>

### @ParameterizedTest、@ValueSource

```java
@ParameterizedTest
@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
void palindromes(String candidate) {
    assertTrue(StringUtils.isPalindrome(candidate));
}
```

<slide>

### @BeforeEach、@AfterEach、@BeforeAll、@AfterAll

```java
class TestingAStackDemo {

    Stack<Object> stack;

    @BeforeEach
    void createNewStack() {
        stack = new Stack<>();
        stack.push("element");
    }

    @Test
    void throwsExceptionWhenPopped() {
        stack.pop();
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void pushAndCount() {
        stack.push("an element");
        assertEquals(2, stack.size());
    }
}
```

<slide>

# Spring Test

- `@SpringBootTest` 为测试类添加 Spring Boot 特性（如默认使用 `SpringBootContextLoader`、允许自定义 properties 等、启动 webClient 等）
- `@AutoConfigureMockMvc` 为测试类 mock 一个 `MockMvc` 实例，该实例可以用来发起请求调用
- `@MockBean` 为任意 Spring Bean 添加 mock 实例，底层实现用的是 Mockito。

<slide>

### @SpringBootTest，@AutoConfigureMockMvc

```java
@SpringBootTest
@AutoConfigureMockMvc
class MyMockMvcTests {

    @Autowired
    private MockMvc mockMvc

    @Test
    void exampleTest() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string("Hello World"));
    }

}
```

<slide>

### @MockBean

```java
@SpringBootTest
class MyTests {

    @Autowired
    private Reverser reverser;

    @MockBean
    private RemoteService remoteService;

    @Test
    void exampleTest() {
        given(this.remoteService.getValue()).willReturn("spring");
        String reverse = this.reverser.getReverseValue();
        assertThat(reverse).isEqualTo("gnirps");
    }

}
```

<slide>

# Mockito

<slide>

# AssertJ，Hamcrest，JSONassert，JsonPath

<slide>

### AssertJ

```java
import static org.assertj.core.api.Assertions.*;

@Test
void applicationArgumentsPopulated(@Autowired ApplicationArguments args) {
    assertThat(args.getOptionNames()).containsOnly("app.test");
    assertThat(args.getOptionValues("app.test")).containsOnly("one");
}
```

<slide>

### Hamcrest

```java
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Test
public void testEquals() {
    Biscuit theBiscuit = new Biscuit("Ginger");
    Biscuit myBiscuit = new Biscuit("Ginger");
    assertThat(theBiscuit, equalTo(myBiscuit));
}
```

<slide>

### JSONassert

```java
JSONObject data = getRESTData("/friends/367.json");
String expected = "{friends:[{id:123,name:\"Corby Page\"},{id:456,name:\"Carter Page\"}]}";
JSONAssert.assertEquals(expected, data, false);
```

<slide>

### JsonPath

```java
String json = "...";
Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

String author0 = JsonPath.read(document, "$.store.book[0].author");
String author1 = JsonPath.read(document, "$.store.book[1].author");
```

```json
{
    "store": {
        "book": [
            {
                "category": "reference",
                "author": "Nigel Rees",
                "title": "Sayings of the Century",
                "price": 8.95
            },
            {
                "category": "fiction",
                "author": "Evelyn Waugh",
                "title": "Sword of Honour",
                "price": 12.99
            },
            {
                "category": "fiction",
                "author": "Herman Melville",
                "title": "Moby Dick",
                "isbn": "0-553-21311-3",
                "price": 8.99
            },
            {
                "category": "fiction",
                "author": "J. R. R. Tolkien",
                "title": "The Lord of the Rings",
                "isbn": "0-395-19395-8",
                "price": 22.99
            }
        ],
        "bicycle": {
            "color": "red",
            "price": 19.95
        }
    },
    "expensive": 10
}
```

<slide>

## 例子

<slide>

```java
@Data
public class Cart {

  @Id
  private ObjectId id;
  private String user;
  private List<Item> items;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Item {

    private String name;
    private Integer quantity;
    private Integer price;
  }
}
```

<slide>

```java
public interface CartRepository extends MongoRepository<Cart, ObjectId> {

  Cart findOneById(ObjectId id);
}
```

<slide>

```java
@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartRepository cartRepository;

  @GetMapping("/total")
  public Integer getTotalPrice(@RequestParam ObjectId cartId) {
    Cart cart = cartRepository.findOneById(cartId);
    if (cart == null) {
      return 0;
    }
    return cart.getItems().stream().map(item -> item.getPrice() * item.getQuantity()).reduce(0, Integer::sum);
  }
}
```

<slide>

```java
@WebMvcTest(controllers = CartController.class)
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CartRepository cartRepository;

  @Test
  void totalPrice() throws Exception {
    ObjectId cartId = ObjectId.get();
    given(cartRepository.findOneById(cartId)).willReturn(mockStationeryCart());
    MockHttpServletRequestBuilder request = get("/cart/total");
    request.queryParam("cartId", cartId.toString());
    mockMvc.perform(request).andExpect(content().string("95"));
  }

  @Test
  void cartNotExist() throws Exception {
    ObjectId cartId = ObjectId.get();
    given(cartRepository.findOneById(cartId)).willReturn(null);
    MockHttpServletRequestBuilder request = get("/cart/total");
    request.queryParam("cartId", cartId.toString());
    mockMvc.perform(request).andExpect(content().string("0"));
  }

  private Cart mockStationeryCart() {
    Cart cart = new Cart();
    cart.setId(ObjectId.get());
    cart.setUser("customer");
    List<Cart.Item> items = new ArrayList<>();
    items.add(new Cart.Item("pen", 15, 5));
    items.add(new Cart.Item("ruler", 2, 10));
    cart.setItems(items);
    return cart;
  }
}
```

# CSS Grid 布局

## 介绍

CSS 网格布局是一种基于二维网格的布局系统，与过去的任何 Web 布局系统相比，它彻底改变了我们设计用户界面的方式。 CSS一直被用来布局我们的网页，但它从来没有做得很好。首先，我们使用了表格，然后是浮点数、定位和内联块，但是所有这些方法本质上都是 hack，并且遗漏了很多重要的功能（例如垂直居中）。

## 浏览器支持

https://caniuse.com/css-grid

## Grid 基本概念

```html
<!-- 网格容器 Grid Container-->
<div class="container" style="display: grid;">
    <!-- 网格项 -->
    <div class="item item-1"> </div>
    <div class="item item-2"> </div>
        <!-- 不是网格项 -->
        <p class="sub-item"> </p>
    <div class="item item-3"> </div>
</div>
```

- 网格分隔线 Grid Line

    ![网格线](https://css-tricks.com/wp-content/uploads/2018/11/terms-grid-line.svg)

- 网格单元 Grid Item

    ![网格单元](https://css-tricks.com/wp-content/uploads/2018/11/terms-grid-cell.svg)

- 网格的列或行 Grid Track

    ![网格的列或行](https://css-tricks.com/wp-content/uploads/2021/08/terms-grid-track.svg)

- 网格区域 Grid Area

    由任意数量的网格单元组成

    ![网格区域](https://css-tricks.com/wp-content/uploads/2018/11/terms-grid-area.svg)

## Grid 属性

### 父元素属性

- display

    ```css
    /* 将元素定义为网格容器并为其内容建立新的网格格式化上下文。Values: grid | inline-grid*/
    .container {
    display: grid | inline-grid;
    }
    ```

- grid-template-columns、grid-template-rows

    ```css
    /* 定义网格的列和行。Values:track-size line-name track-size */
    .container {
    grid-template-columns: ...  ...;
    /* e.g.
        1fr 1fr
        minmax(10px, 1fr) 3fr
        repeat(5, 1fr)
        50px auto 100px 1fr
    */
    grid-template-rows: ... ...;
    /* e.g.
        min-content 1fr min-content
        100px 1fr max-content
    */
    }
    ```

    ![网格区域](https://css-tricks.com/wp-content/uploads/2018/11/template-columns-rows-01.svg)

    ```css
    /* 显式命名分隔线。 */
    .container {
    grid-template-columns: [first] 40px [line2] 50px [line3] auto [col4-start] 50px [five] 40px [end];
    grid-template-rows: [row1-start] 25% [row1-end] 100px [third-line] auto [last-line];
    }
    ```

     ![网格区域](https://css-tricks.com/wp-content/uploads/2018/11/template-column-rows-02.svg)

- grid-template-areas

    ```css
    /* 通过引用使用 grid-area 属性指定的网格区域的名称来定义网格模板 */
    /* <grid-area-name> 网格区域的名字 */
    /* . 空网格单元 */
    /* none 没有定义 */
    .container {
    grid-template-areas:
        "<grid-area-name> | . | none | ..."
        "...";
    }
    ```

    ```css
    .item-a {
    grid-area: header;

    }
    .item-b {
    grid-area: main;
    }
    .item-c {
    grid-area: sidebar;
    }
    .item-d {
    grid-area: footer;
    }
    /* 每一行需要相同数量的单元格*/
    .container {
    display: grid;
    grid-template-columns: 50px 50px 50px 50px;
    grid-template-rows: auto;
    grid-template-areas:
    "header header header header"
    "main main . sidebar"
    "footer footer footer footer";
    }
    ```

     ![网格区域](https://css-tricks.com/wp-content/uploads/2018/11/dddgrid-template-areas.svg)

    区域两端的线会自动命名，区域最左边的线有三个名字：header-start、main-start、footer-start。

- grid-template

    ```css
    /*    grid-template-rows、grid-template-columns 和 grid-template-areas 的简写*/
    .container {
    grid-template: none | <grid-template-rows> / <grid-template-columns>;
    }
    ```

    ```css
    .container {
    grid-template:
    [row1-start] "header header header" 25px [row1-end]
    [row2-start] "footer footer footer" 25px [row2-end]
    / auto 50px auto;
    }
    /* 等同于 */
    .container {
    grid-template-rows: [row1-start] 25px [row1-end row2-start] 25px [row2-end];
    grid-template-columns: auto 50px auto;
    grid-template-areas:
    "header header header"
    "footer footer footer";
    }
    ```

- column-gap、row-gap、grid-column-gap、grid-row-gap

    ```css
    .container {
    /*   定义在行、列之间的间隔 */
    /* standard supported in Chrome 68+, Safari 11.2 Release 50+, and Opera 54+.*/
    column-gap: <line-size>;
    row-gap: <line-size>;

    /* old */
    grid-column-gap: <line-size>;
    grid-row-gap: <line-size>;
    }
    ```

    ```css
    .container {
    grid-template-columns: 100px 50px 100px;
    grid-template-rows: 80px auto 80px;
    column-gap: 10px;
    row-gap: 15px;
    }
    ```

    ![间隙](https://css-tricks.com/wp-content/uploads/2018/11/dddgrid-gap.svg)

- gap、grid-gap

    ```css
    .container {
    /*  row-gap and column-gap 简写 */
    /* standard supported in Chrome 68+, Safari 11.2 Release 50+, and Opera 54+.*/
    gap: <grid-row-gap> <grid-column-gap>;

    /* old */
    grid-gap: <grid-row-gap> <grid-column-gap>;
    }

    .container {
    grid-template-columns: 100px 50px 100px;
    grid-template-rows: 80px auto 80px;
    gap: 15px 10px;
    }
    ```

- justify-items

    ```css
    .container {
    /* 沿内联（行）轴对齐网格内单元格 stretch 为默认属性 */
    justify-items: start | end | center | stretch;
    }
    ```

    ![justify-items-start](https://css-tricks.com/wp-content/uploads/2018/11/justify-items-start.svg)

    ![justify-items-end](https://css-tricks.com/wp-content/uploads/2018/11/justify-items-end.svg)

    ![justify-items-center](https://css-tricks.com/wp-content/uploads/2018/11/justify-items-center.svg)

    ![justify-items-stretch](https://css-tricks.com/wp-content/uploads/2018/11/justify-items-stretch.svg)

- align-items

    ```css
    .container {
    /* 沿内联（列）轴对齐网格内单元格 stretch 为默认属性 */
    align-items: start | end | center | stretch | baseline ;
    }
    ```

    ![align-items-start](https://css-tricks.com/wp-content/uploads/2018/11/align-items-start.svg)

    ![align-items-end](https://css-tricks.com/wp-content/uploads/2018/11/align-items-end.svg)

    ![align-items-center](https://css-tricks.com/wp-content/uploads/2018/11/align-items-center.svg)

    ![align-items-stretch](https://css-tricks.com/wp-content/uploads/2018/11/align-items-stretch.svg)

- place-items

    ```css
    /*  place-items 简写 <align-items> / <justify-items> */
    .center {
    display: grid;
    /*  <align-items> <justify-items> 相同 */
    place-items: center;
    }
    ```

- justify-content

    ```css
    /*  此属性沿内联（行）轴对齐网格 */
    .container {
    justify-content: start | end | center | stretch | space-around | space-between | space-evenly;
    }
    ```

    ![justify-content-start](https://css-tricks.com/wp-content/uploads/2018/11/justify-content-start.svg)

    ![justify-content-end](https://css-tricks.com/wp-content/uploads/2018/11/justify-content-end.svg)

    ![justify-content-center](https://css-tricks.com/wp-content/uploads/2018/11/justify-content-center.svg)

    ![justify-content-stretch](https://css-tricks.com/wp-content/uploads/2018/11/justify-content-stretch.svg)

    ![justify-content-space-around](https://css-tricks.com/wp-content/uploads/2018/11/justify-content-space-around.svg)

    ![justify-content-space-between](https://css-tricks.com/wp-content/uploads/2018/11/justify-content-space-between.svg)

     ![justify-content-space-evenly](https://css-tricks.com/wp-content/uploads/2018/11/justify-content-space-evenly.svg)

- align-content

    ```css
    /*  此属性沿内联（列）轴对齐网格 */
    .container {
    align-content: start | end | center | stretch | space-around | space-between | space-evenly;
    }
    ```

    ![align-content-start](https://css-tricks.com/wp-content/uploads/2018/11/align-content-start.svg)

    ![align-content-end](https://css-tricks.com/wp-content/uploads/2018/11/align-content-end.svg)

    ![align-content-center](https://css-tricks.com/wp-content/uploads/2018/11/align-content-center.svg)

    ![align-content-stretch](https://css-tricks.com/wp-content/uploads/2018/11/align-content-stretch.svg)

    ![align-content-space-around](https://css-tricks.com/wp-content/uploads/2018/11/align-content-space-around.svg)

    ![align-content-space-between](https://css-tricks.com/wp-content/uploads/2018/11/align-content-space-between.svg)

     ![align-content-space-evenly](https://css-tricks.com/wp-content/uploads/2018/11/align-content-space-evenly.svg)

- place-content

    ```css
    /*  place-content 简写 <align-content> / <justify-content> */
    .center {
    display: grid;
    /*  <align-content> <justify-content> 相同 */
    align-content: center;
    }
    ```

- grid-auto-columns、grid-auto-rows

    ```css
    .container {
    grid-auto-columns: <track-size> ...;
    grid-auto-rows: <track-size> ...;
    }

    .container {
    grid-template-columns: 60px 60px;
    grid-template-rows: 90px 90px;
    }
    ```

    ![grid-auto-columns-rows-01](https://css-tricks.com/wp-content/uploads/2018/11/grid-auto-columns-rows-01.svg)

    ```css
    .item-a {
    /* 2列的第1列 */
    grid-column: 1 / 2;
    /* 3行的第2行 */
    grid-row: 2 / 3;
    }
    .item-b {
    grid-column: 5 / 6;
    grid-row: 2 / 3;
    }
    ```

     ![grid-auto-columns-rows-02](https://css-tricks.com/wp-content/uploads/2018/11/grid-auto-columns-rows-02.svg)

    我们告诉 .item-b 从第 5 列开始，到第 6 列结束，但我们从未定义第 5 或 6 列。因为我们引用了不存在的行，所以会创建宽度为 0 的隐式轨道来填充在缝隙中。

    ```css
    .container {
    grid-auto-columns: 60px;
    }
    ```

    ![grid-auto-columns-rows-03](https://css-tricks.com/wp-content/uploads/2018/11/grid-auto-columns-rows-03.svg)

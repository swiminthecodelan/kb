例如 HTML 元素没有 `color` 和 `font-size` 属性则会继承父元素的值。所以有这些属性的 HTML 元素的所有子元素都继承。这就是 **cascading of styles**，命名为 CSS，`Cascading Style Sheets` 的原因。这就是浏览器构建 CSSOM 的原因，CSSOM 是一种基于 CSS 级联规则计算样式的树状结构。

![alt](https://miro.medium.com/max/357/1*DJg1yRx-AzkZposWbJKcaA.png)

CSSOM 树不包含不在页面上打印的元素，例如 `<link>`，`<title>`，`<script>`。

### Render Tree

Render-Tree 也是树状结构的，由 DOM 和 CSSOM 组合而成。浏览器必须计算出每个可见元素的布局并绘制在屏幕上，因此浏览器使用 Render-Tree。因此，没有构建 Render-Tree，屏幕上不会打印任何内容，这就是我们需要 DOM 和 CSSOM 树的原因。

Render-Tree 作为打印屏幕内容的底层，不包含在像素矩阵中没有任何位置的 nodes。例如，`display:none;` 元素尺寸为 `0px` X `0px`，因此不会出现在 Render-Tree 中。然而，如果元素具有的是 `visibility:hidden` 或 `opacity:0`，它们将占据屏幕上的空间，因此会出现在 Render-Tree 中。

![alt](https://miro.medium.com/max/700/1*8HnhiojSoPaJAWkruPhDwA.png)

与 DOM 不同，CSSOM 不允许开发者访问浏览器构建 CSSOM 树中的节点。但既然浏览器将 DOM 与 CSSOM 相结合组成 Render-Tree，浏览器通过 DOM 提供的高层次 API 暴露了该 DOM 节点 的 CSSOM 节点。这有助于开发者访问或更改 CSSOM 节点的 CSS 属性。

## Rendering Sequence

理解了什么是 DOM，CSSOM，Render-Tree，现在让我们理解浏览器是如何使用它们渲染一个典型的页面。对于任何 Web 开发人员来说，对这个过程有一个最低限度的了解是至关重要的，因为它将帮助我们设计我们的网站以获得最大的用户体验和性能。

当一个页面加载完成，浏览器首先解析 HTML 文本并构建 DOM 树。然后将各渠道的 CSS 构建一个 CSSOM 树。

当这些树构建完成时，再由这些树构建一个 Render-Tree。一旦 Render-Tree 构建完成，浏览器开始逐个在页面上渲染元素。

### Layout operation

浏览器第一步创建每个 Render-Tree 中节点的的布局。布局包括每个节点的**大小**以及在屏幕上的位置。这个过程被称作布局是因为浏览器计算每个节点的布局信息。

这个过程也被叫做 **reflow** 或者是 **browser reflow**，也会发生在 **scroll**，窗口 resize 或操作 DOM 元素。

### Paint operation

现在，有一个需要在屏幕上打印的几何图形列表。由于 Render-Tree 中的元素可以互相重叠并且它们有独立的动态样式，浏览器创建独立的 **layer**。

创建 layer 帮助浏览器通过页面的生命周期有效执行绘画操作，比如在 scroll 或浏览器窗口 resize 时。layer 也可以帮助浏览器按正确的堆叠顺序 `z-index` 绘制。

现在我们有了很多 layer，可以组合它们并在屏幕上绘制它们。但是浏览器不一次性绘制所有的 layer，而是逐个 layer 绘制。浏览器为任何元素有的可见属性填充单个像素例如 border，background colorm，shadow，text 等等。此过程也被称作**光栅化**。为了提升性能，浏览器会使用不同的线程光栅化。

### Compositing operation

我们拥有的是不同的图层，它们应该以特定的顺序绘制在屏幕上。通过组合操作，这些图层发送至 GPU 最终渲染到页面上。

发送整个图层进行绘制显然是低效的，因为每次 **reflow** 和 **repaint** 都会重新发送整个图层。因此，一个图层会被分成几块在屏幕上进行绘制。

![alt](https://miro.medium.com/max/700/1*yQJkz12sPxS-kJoMDqzbEQ.png)

### Browser engines

创建 DOM 树、CSSOM 树，处理渲染逻辑是通过浏览器内部的 **Browser Engine** 软件完成的。浏览器引擎包含所有必要的元素和逻辑，可将网页从 HTML 代码呈现为屏幕上的实际像素。

WebKit 就是一个 browser engine。苹果的 Safari 浏览器就是用的 WebKit，也是过去谷歌 Chrome 浏览器默认的渲染引擎。现在 Chromium 项目使用 Blink 作为默认渲染引擎。

## Rendering Process in browsers

JavaScript 是使用 ECMAScript 进行标准化的。实际上自从 JavaScript 是一个注册商标，我们仅仅称之为 ECMAScript。因此，每一个 JS 引擎都必须遵循标准的规范，比如 V8，Chakra,Spider Monkey。

拥有一个规范可以让我们在所有 JavaScript 运行时（例如浏览器、Node、Deno 等）中获得一致的 JavaScript 体验。这对于在多个平台上一致且完美地开发 JavaScript（和 Web）应用程序非常有用。

但是，浏览器呈现事物的方式并非如此。HTML，CSS 或 JavaScript，这些语言是标准化的。但是，浏览器统一管理并渲染不是规范的。谷歌 Chrome 和苹果 Safari 渲染的时候会有一些区别。

因此，很难去推测一个浏览器渲染的渲染顺序和机制。HTML5 规范对理论上如何渲染做出了一些标准化，不过浏览器怎样实现最终取决于它们。

尽管存在一些不同的地方，但在所有浏览器中存在一些共同的原则。

### Parsing and External Resources

读取并解析 HTML 并构建一个 DOM 树，这个过程被称作 DOM parsing，执行这个过程的程序叫做 DOM parser。

大多数浏览器提供 DOMParser Web API 解析 HTML 代码中构建一个 DOM 树。一个 DOMParser 类的实例代表一个 DOM parser,使用 parseFromString 原型方法将原生 HTML 代码转化为一个 DOM 树。

![alt](https://miro.medium.com/max/700/1*DTO0PBRawrEdZakWloQVjg.png)

当浏览器请求一个页面并获取到返回的 HTML 文本（header `ContentType`，值为 `text/html`），只要整个文档的几个字符或行可用，浏览器就可以开始解析 HTML。因此浏览器可以逐渐构建 DOM 树，每次构建一个节点。浏览器因 HTML 是一个嵌套树状结构从上到下而不是从中间的某个地方进行解析。

![alt](https://miro.medium.com/max/700/1*1bcaVVjG_077zHVzGfHUyw.gif)

Timing 行中的这些事件通常称为性能指标。当这些事件尽可能靠近并尽早发生时，用户体验会更好。`FP` 全称是 First Paint，意味着浏览器开始在屏幕上渲染的时间。`FCP` 全称是 `First Contentful Paint` 意味着渲染文本或图像等内容的一个像素的时间。`LCP` 全称是 `Largest Contentful Paint` 浏览器渲染了最大的图片或文本的时间。`L` 代表 `window` 对象触发 `onload` 事件的时间。`DCL` 全称是 `DOMContentLoaded`，意味着 `document` 对象触发的事件，它会冒泡到 `window`，因此可以在 `window` 上监听它。

当浏览器遇到**外部资源**时，例如一个 `<script src="url"></script>` script 文件，一个 `<link rel="stylesheet" href="url"/>` CSS 文件，一个 `<img src="url" />` 图片文件，浏览器会开始在后台下载（远离执行 JavaScript 的主线程）。

DOM 解析是发生在**主线程**的。所以如果主 JS 执行线程繁忙，DOM 解析会不会进行，因为 `script` 元素，script 文件是 `parser-blocking` 的。所有 `image`、`stylesheet`、`pdf`、`video` 外部文件请求不会阻塞 DOM 解析构建。

### Parser-Blocking Scripts

parser-blocking script 是一个阻止解析 HTML 的 script 文件。当浏览器遇到包含嵌入式 `script` 元素，会先执行脚本然后再继续解析 HTML 构建 DOM 树。所以所有的嵌入式 script 都是 parser-blocking。

如果 `script` 元素是一个外部文件，浏览器会**暂停**主线程直到文件下载完成。这意味着不再进行 DOM parsing 知道脚本下载完成。一旦脚本下载完成，浏览器将首先在主线程执行下载好的脚本文件，然后再继续 DOM parsing。如果浏览器再次在 HTML 中找到另一个脚本元素，它将执行相同的操作。那么为什么浏览器必须停止 DOM 解析，直到 JavaScript 被下载并执行呢？

浏览器暴露 DOM API 给 JS 运行时，意味着我们可以再 JS 中访问和操作 DOM 元素，这就是 React、Angular 典型 web 框架的工作原理。但如果浏览器想要同时执行 DOM parsing 和 script，那就会在 DOM parser 线程和主线程之间产生竞争条件。因此 DOM parsing 必须发生在主线程。

然而，在大多数情况下在后台下载外部脚本文件时没必要暂停 DOM parsing。因此 HTML5 在 `script` 标签上添加了 `async` 属性。当 DOM parser 遇到外部的 `script` 元素时，在后台下载外部脚本文件时不会暂停解析，仍然在执行脚本文件时暂停 parsing。`defer` 在后台下载外部脚本文件时不会暂停解析，直到 DOM 树构建完毕再执行脚本。

![alt](https://miro.medium.com/max/700/1*5xdQ1j6Ai2PZYCRQuXM5wg.gif)

一些浏览器包含一种推测性的解析策略，将 HTML 解析和 DOM 构建使用不同的线程，提前解析 `link`、`script`、`img` 等等标签并下载对应资源。如果你有三个一个接一个的 `script` 标签，这很有帮助。但是浏览器不会开始下载第二个 script，直到第一个脚本下载完，因为 DOM parser 不会解析第二个 `script` 元素。可以添加 `async` 属性异步下载脚本，但是保证不了执行顺序。

所谓推测性解析策略，就是浏览器推测特定资源预计将在未来加载，所以更好的方式是现在就在后台进行下载。但如果 JS 删除了某些引用外部资源的元素，那这些预加载的资源就没用了。

### Render-Blocking CSS

除了 parser-blocking 脚本文件任何外部资源请求都不会阻塞 DOM parsing。因此 CSS 也不会直接阻塞 DOM parser。

浏览器引擎使用服务器获取的文本文档形式的 HTML 构建 DOM 树。相似的，它使用样式表内容构建 CSSOM 树，例如从外部 CSS 文件或 HTML 中的嵌入（以及内联）CSS。DOM 和 CSSOM 树是同时在主线程中构建的。彼此组合成 Render Tree 并打印在页面。Render Tree 与 DOM Tree 一样都是逐渐生成的，但是 CSSOM 树不是。

当浏览器找到一个 `<style>` 块或者是行内样式，将会解析**所有**内嵌的 CSS 并更新 CSSOM 树。之后，继续以正常的方式解析 HTML。另一种情况是浏览器遇到一个外部样式表。与外部脚本文件不同，外部样式表不是 parser-blocking 资源，因此浏览器会在后台下载并继续 DOM parsing。

与 HTML 文件构建 DOM 的方式不同，浏览器不会一个一个字节的根据样式表文件并加入 CSSOM 树，原因是在文件尾部的样式有可能也会覆盖头部样式。因此如果采取逐渐生成 CSSOM 树的话，将会因为**样式不断被覆盖**导致 CSSOM 树不断改变，**Render Tree** 不断渲染。看到元素在屏幕上改变样式将是一种不愉快的用户体验。由于 CSS 样式是级联的，因此一项规则更改会影响许多元素。

CSSOM 树在处理完样式表所有的 CSS 后立即更新，然后 Render Tree 也会随之更新并渲染在屏幕上。

CSS 是一个 render-blocking 资源。一旦浏览器请求获取一个外部样式表，Render Tree 构建将会暂停。因此关键渲染路径也被卡住，屏幕上不会渲染任何东西。然而，当后台在下载外部样式表时，DOM 树仍然会继续构建。

![alt](https://miro.medium.com/max/700/1*y3QmSfyergjmVV32nH7tPA.gif)

浏览器使用了旧状态的 CSSOM tree 生成 Render Tree，随着 HTML 被解析以逐步在屏幕上呈现内容。在这种情况下，一旦外部样式表下载完成，解析生成新的 CSSOM 树，Render Tree 也会随之更新并渲染。新的样式会覆盖旧的样式，也会导致非常差的用户体验 Flash of Unstyled Content。因此建议尽早加载所有外部样式表，在 `head` 部分内。

CSSOM 提供了高层次的 JS API 操作 DOM 元素的样式。在后台下载样式表时，主线程仍然可以执行 JS，并在 JS 中获取 DOM 元素的样式。当样式表下载完成后，CSSOM 会更新并改变 DOM 元素的 CSS properties。更新之前 JS 中获取的 DOM 元素的样式就会失效。因此在后台下载样式表时执行 JS 是不安全的。

根据 HTML5 规范，浏览器可以下载一个脚本文件，但除非所有之前的样式表都已解析，不然不会执行。样式表阻塞了 script 的执行，因此将这种样式表叫做 script-blocking 样式表或者是 script-blocking CSS。

![alt](https://miro.medium.com/max/700/1*atsh0R6Do25SriYvvskkgA.gif)

### Document’s `DOMContentLoaded` Event

`DOMContentLoaded` 事件标记浏览器完整构建并可以安全访问 DOM 树的时间。触发 `DCL` 事件包含了许多因素。

如果 HTML 不包含任何脚本，DOM parsing 不会阻塞。当 HTML 解析完成后，浏览器将尽可能快的触发 `DCL`。如果有 parser-blocking 脚本，`DCL` 则必须等到脚本下载并执行完成。在没有外部脚本的情况下，`DCL` 会等待样式表加载。`DCL` 标记了整个 DOM 树的就绪时间，但 CSSOM 完全构建之前，访问 DOM 的样式信息是不安全的。因此大多数浏览器都会等待所有的外部样式加载并解析。

script-blocking 样式表会延迟 `DCL`。在这种情况下，脚本会等待该样式表加载完成，DOM 树不会构建完成。

`DCL` 是一个网站性能的指标。应该优化 `DCL`，将其尽可能早的触发。一个最佳实践是对 `script` 元素使用 `defer` 和 `async`,浏览器便可以在后台下载脚本时执行其他任务。第二点，应该优化 script-blocking 和 render-blocking 样式表。

### Window's `load` event

JS 会阻塞 DOM 树的构建，但是对于外部样式表以及 images、videos 等文件不是这样。

`load` 标记了外部样式表和文件下载完成，浏览器结束下载的时间。

![alt](https://miro.medium.com/max/700/1*DuLBecXpJjFh1qnakXjWWg.png)

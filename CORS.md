# CORS

CORS 是一个 W3C 标准，全称是“跨域资源共享”（Cross-origin resource sharing）。它允许浏览器向跨域的服务器，发出XMLHttpRequest请求，从而克服了 AJAX 只能同源使用的限制。

## 简单请求与非简单请求

同时满足请求方法、HTTP 头信息。表单请求规避 CORS，非简单请求采用新的处理方式。

- 方法：HEAD、GET、POST
- 头信息：Accept、Accept-Language、Content-Language、Last-Event-ID、Content-Type:application/x-www-form-urlencoded、multipart/form-data、text/plain。

## 简单请求

直接发出 CORS 请求，增加一个 Origin 字段，表明域（协议 + 域名 + 端口）。服务器许可则在头信息中包含 Access-Control-Allow-Origin 、Access-Control-Allow-Credentials、Access-Control-Allow-Headers。

CORS 请求默认不包含 Cookie 信息，需要浏览器将 withCredentials 设为 true，同时服务器的 Access-Control-Allow-Origin 不为 * 及 Access-Control-Allow-Credentials 为 true。否则就会[报错](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/CORS/Errors)。Cookie 遵循同源政策，意味着只有服务器的 Cookie 才会上传。

## 非简单请求

对服务器提出特殊要求的请求，比如请求方法是 PUT、Detele，或 Content-Type 为 application/json。

非简单请求在正式请求之前增加一次 HTTP 查询请求，称为预检请求（preflight），询问服务器网页域名是否在许可名单以及允许使用的 HTTP 方法和头信息字段。只有得到肯定答复浏览器才会发出正式的请求。11111432423

预检请求的用的请求方法是 OPTIONS，头信息携带 Origin。还包括 Access-Control-Request-Method 列出用到哪些 HTTP 方法、Access-Control-Request-Headers 指定额外发送的头信息字段。

服务器返回的 Access-Control-Allow-Origin 不包括浏览器的域则表明不同意。还包括  Access-Control-Allow-Credentials、Access-Control-Allow-Headers、Access-Control-Request-Method、Access-Control-Max-age。

预检通过后会发送正常的请求，与简单请求一样，浏览器请求会包含 Origin 和服务器返回包含 Access-Control-Allow-Origin。

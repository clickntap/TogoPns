[#include "body-begin.ftl"]
<style>
pre {
	margin:20px;
}
</style>
<pre>
STRIPECUBE SMART FRAMEWORK ${this.version} <i class="fa fa-fw fa-copyright"></i> stripecube.com<br>
[#if exception.cause??]
<b>${(exception.cause.class.name)!} (${(exception.class.name)!})</b><br>
${(exception.cause.message)!}
${exception.message!}
[#else]
<b>${(exception.class.name)!}</b><br>
${exception.message!}
[/#if]
</pre>
[#include "body-end.ftl"]


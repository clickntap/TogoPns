[#include "macro.ftl"]

[#if this.param("target") == ""]
  [boundary]inner:body[/boundary]
  [#include "body.ftl"]
  [boundary]script[/boundary]
  [#include "script.ftl"]
[/#if]

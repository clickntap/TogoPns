[#compress]
[#include "macro.ftl"]
[#include "body-begin.ftl"]
<script src="/ui/togopns/js/togopns-min.js"></script>

<div id="body">
    [#include "body.ftl"]
</div>

<script>
    $(window).ready(function () {
        [#include "script.ftl"]
        loadUi();
    });
</script>

[#include "body-end.ftl"]
[/#compress]

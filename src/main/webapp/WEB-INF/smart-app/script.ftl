[#if !this.authenticated]
	[#if this.param("action") == "smartLogin"]
	$('#signin form').effect("shake", { times:3 }, 1000)
	[/#if]
[/#if]

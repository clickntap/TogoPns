<div class="container-fluid">
	<div id="signin">
		<form role="form" onsubmit="return uif(this)">
		  <div class="form-group">
		    <label>Username</label>
		    <input type="text" class="form-control" name="username">
		  </div>
		  <div class="form-group">
		    <label>Password</label>
		    <input type="password" class="form-control" name="password">
		  </div>
		  <div class="checkbox">
		    <label>
		      <input type="checkbox" name="smartRememberMe"> Remember Me
		    </label>
		  </div>
		  <input type="hidden" name="action" value="smartLogin">
		  <button type="submit" class="btn btn-default btn-sm">Sign In <i class="fa fa-sign-in fa-fw"></i></button>
		</form>
	</div>
</div>
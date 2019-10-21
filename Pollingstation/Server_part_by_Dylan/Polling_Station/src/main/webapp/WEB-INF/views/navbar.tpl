
<!--Begin navbar-->
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand" href="./index.php">Newcastle University E-Voting</a>
				<div class="nav-collapse collapse">
					{if isset($username)}
						<span class="navbar-text pull-right">
							 <a href="./logout.php" class="navbar-link">(Log out)</a>
						</span>
					{else}
						<span class="navbar-text pull-right">
							<a href="./manage.php" class="navbar-link">(Log in)</a>
						</span>
					{/if}
                    <ul class="nav">
					{if isset($username)}
						{if $highlighted == "coordinator"}
							<li class="active"><a href="./coordinator.php">Create Election</a></li>
						{else}
							<li><a href="./coordinator.php">Create Election</a></li>
						{/if}
						{if $highlighted == "manageelections"}
							<li class="active"><a href="./manage.php">Manage Elections</a></li>
						{else}
							<li><a href="./manage.php">Manage Elections</a></li>						
						{/if}
					{else}
						<li><a href="./index.php">Home</a></li>
					{/if}
					
					{if $highlighted == "bulletinboard"}
						<li class="active"><a href="./bulletin_board.php">Bulletin Board</a></li>					
					{else}
						<li><a href="./bulletin_board.php">Bulletin Board</a></li>
					{/if}
					{if $highlighted == "help"}
						<li class="active"><a href="./help.php">Help</a></li>					
					{else}
						<li><a href="./help.php">Help</a></li>
					{/if}
					</ul>
                </div><!--/.nav-collapse -->
        </div>
    </div>
</div>
<!--End Nav Bar -->



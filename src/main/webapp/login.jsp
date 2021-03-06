<!DOCTYPE html>

<html ng-app="Login">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>MalisonWeb | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="./assets/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="./assets/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="./assets/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page" ng-controller="loginCtrl">
<div class="login-box">
  <div class="login-logo">
    <a href="../../index2.html"><b>Malison</b>WebApp</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>

    <form name="form" ng-submit="login()">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="Username" ng-model="username">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Password" ng-model="password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat" ng-disabled="form.$invalid || dataLoading" value="submit">Sign In</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <a href="user.jsp" class="text-center">Register a new membership</a>
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.0 -->
<script src="./assets/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="./assets/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script type="text/javascript" src="./assets/angular/angular-1.5.7/angular.min.js"></script>
<script type="text/javascript" src="./assets/angular/angular-1.5.7/angular-cookies.min.js"></script>

<script type="text/javascript" src="./assets/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="./assets/scripts/jquery-1.9.1.min.js" ></script>
<script src="./assets/scripts/jquery-ui-1.10.1.custom.min.js" ></script>
<script type="text/javascript" src="./assets/login.js"></script>
</body>
</html>

<!DOCTYPE html>
<html ng-app="Invoice">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Invoice Pdf Generator</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="./assets/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="./assets/plugins/datatables/dataTables.bootstrap.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="./assets/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="./assets/dist/css/skins/_all-skins.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini" ng-controller="InvoiceCtrl">
<div class="wrapper">

  <header class="main-header">
    <!-- Logo -->
    <a href="" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>I</b>PDF</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Inv</b>PDF</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>


      <div  class="navbar-custom-menu">
        <!--enter some text here-->
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
     <ul class="sidebar-menu">
        <li class="header">MENU</li>
         <li><a href="./index.jsp"><i class="fa fa-circle-o"></i> Jobs</a></li>
         <li class="treeview">
              <a href="#">
                <i class="fa fa-circle-o"></i> <span>Invoice</span> <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li>
                  <a href="./invoice.jsp"><i class="fa fa-circle-o"></i> Create new invoice</a>
                </li>
                <li>
                  <a href="invoiceview.jsp"><i class="fa fa-circle-o"></i>View Invoices</a>
                </li>
              </ul>
            </li>
        <li class="treeview">
          <a href="#">
            <i class="fa fa-circle-o"></i> <span>Actions</span> <i class="fa fa-angle-left pull-right"></i>
          </a>
          <ul class="treeview-menu">
            <li> <button class="btn btn-danger" ng-click="deleteJobs()"><i class="fa fa-circle-o"></i> Delete</button></li>
            <li> <button class="btn btn-primary" ng-click="editJobs()"><i class="fa fa-circle-o"></i>Edit</button></li>
          </ul>
        </li>
        </ul>
  </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div>
    <section class = "content" ng-show="showAddCompany" >
           <div class="col-xs-5" >
                  <label> Please Enter The Company name</label>
                  <input type="text" class="form-control" placeholder="Company Name" ng-model="company"><br/>
                  <label>Choose the Currency of the invoice</label><br/>
                  <label for="shillings">Kenyan Shillings</label>
                  <input id="shillings"  type="radio" name="currency" ng-model="currency" value="shillings" checked><br/>
                   <label for="dollars">US Dollars</label>
                  <input id="dollars" type="radio" name="currency" ng-model="currency" value="dollars"><br/>
                  <button class="btn btn-success" ng-click="showhideCompany(false)">Done</button>
            </div>
    </section>
    </div>

    <div ng-show="showAddJobs">
    <section class="content-header">
      <h1>
        Uninvoiced Job Table
      </h1>
      <button class="btn btn-primary" ng-click="updatejob()">update</button>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-body">
              <table id="example1" class="table table-bordered table-striped">
                <tbody>
                  <thead>
                  <tr>
                    <th>Selected</th>
                    <th>Job id</th>
                    <th>Invoice Number</th>
                    <th>Date</th>
                    <th>Goods</th>
                    <th>Loaded From</th>
                    <th>Destination</th>
                    <th>Vehicle</th>
                    <th>Rate</th>
                    <th>Amount</th>
                  </tr>
                  </thead>                
                  <tr ng-repeat = "jo in jobs">
                    <td><input type ="checkbox" ng-model= "jo.invoiced" ng-click="updateSelection($event, jo.id, jo.invoiced)" ng-checked="isSelected(jo.id)"></td>
                    <td>{{jo.id}}</td>
                    </input></td>
                    <td>{{jo.invoiceNumber}}</td>
                    <td>{{jo.date}}</td>
                    <td>{{jo.product}}</td>
                    <td>{{jo.loadedFrom}}</td>
                    <td>{{jo.destination}}</td>
                    <td>{{jo.vehicleRegno}}</td>
                    <td>{{jo.rate}}</td>
                    <td>{{jo.amount}}</td>
                  </tr>
              </tbody>              
             </table>
             <button class="btn btn-success" value= "save" ng-click="createInvoice()"> Create Invoice</button>
             </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 2.3.3
    </div>
    <strong>Copyright &copy; 2016 <a href="http://almsaeedstudio.com">Invision Itech</a>.</strong> All rights
    reserved.
  </footer>
</div>
<!-- jQuery 2.2.0 -->
<script src="./assets/plugins/jQuery/jQuery-2.2.0.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="./assets/bootstrap/js/bootstrap.min.js"></script>
        <!-- DataTables -->
        <script src="./assets/plugins/datatables/jquery.dataTables.min.js"></script>
        <script src="./assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
        <!-- SlimScroll -->
        <script src="./assets/plugins/slimScroll/jquery.slimscroll.min.js"></script>
        <!-- FastClick -->
        <script src="./assets/plugins/fastclick/fastclick.js"></script>

        <!-- page script -->
        <script src="./assets/scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
        <script src="./assets/scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
        <script src="./assets/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="./assets/angular/angular-1.5.7/angular.min.js"></script>
        <script src="./assets/invoice.js"></script>
        <script src="./assets/dist/js/demo.js"></script>
        <script src="./assets/dist/js/app.min.js"></script>



</body>
</html>

/**
 * Created by mimounchikhi on 06/03/16.
 */
var  cdrest=angular.module("cdrest",[]);


cdrest.controller("ListOperationsController", function($scope, $http) {

    $scope.pageOperations=null;
    $scope.pageCourante=0;
    $scope.size=5;

    $scope.listOperations=function() {
        $http.get("operations?page="+$scope.pageCourante+"&size="+$scope.size)
            .success(function(data) {
                $scope.pageOperations=data;
            });
    };
    $scope.listOperations();
} );


cdrest.controller("CreationController", function($scope, $http) {
    $scope.operation={};
    $scope.errors={};
    $scope.mode={value:"form"};
    $scope.exception={message:null};


    $scope.saveOperation=function() {
        $http.post("operations",$scope.operation)
            .success(function(data) {
                if (!data.errors) {
                    $scope.operation = data;
                    $scope.errors = null;
                    $scope.mode.value='confirm';
                }
                else {
                    $scope.errors = data;
                }
            })
            .error(function(data) {
                $scope.exception.message=data.message;
            });
    };

});

cdrest.controller("HelloController", function($scope) {
    $scope.helloTo = {};
    $scope.helloTo.title = "CDREST Application";
} );

cdrest.controller("IndexController", function($scope, $http) {
    $scope.menu = ["Add", "List", "Logout"];
    $scope.selectedMenu=null;

    $scope.select=function(m) {
        $scope.selectedMenu=m;
    }

} );
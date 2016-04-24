var host = "/image/rest";
app.controller("MyController", function ($scope, $http, $location, $rootScope, $cookieStore, fileReader) {

    $scope.inputFile = {};
    $scope.isLoaded = false;

    var maxWidth = 500;
    var maxHeight = 500;

    $scope.getFile = function () {
        $scope.isLoaded = false;
        $scope.progress = 0;
        fileReader.readAsDataUrl($scope.file, $scope)
            .then(function(result) {
                $scope.imageSrc = result;
                $scope.isLoaded = true;
            });
    };

    $scope.findSimilar = function(){
        var formData = new FormData();
        formData.append("file", $scope.file);
        $http.post(host + '/request/findSimilar', formData, {
            transformRequest: function (data, headersGetterFunction) {
                return data;
            },
            headers: {'Content-Type': undefined},
            accepts: "application/json; charset=utf-8"
        }).success(function (data, status) {
            alert("Success");
        }).error(function (data, status) {
            alert("Error");
        });
    }




    //$http.get(host + '/request/add').success(function (data, status) {
    //    $scope.texData = data;
    //}).error(function (data, status) {
    //    //alert("Error ... " + status);
    //});

});

app.directive("ngFileSelect",function(){

    return {
        link: function($scope,el){

            el.bind("change", function(e){

                $scope.file = (e.srcElement || e.target).files[0];
                $scope.getFile();
            })

        }

    }


})
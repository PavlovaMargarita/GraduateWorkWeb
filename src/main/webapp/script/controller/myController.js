var host = "/image/rest";
app.controller("MyController", function ($scope, $http, $location, $rootScope, $cookieStore, fileReader) {

    $scope.inputFile = {};
    $scope.isLoaded = false;
    $scope.similarImages = [];
    $scope.findSimilarImages = true;
    $scope.retrieveFaceOutputImageUrl = "";

    var maxWidth = 500;
    var maxHeight = 500;

    $scope.getFile = function () {
        $scope.isLoaded = false;
        fileReader.readAsDataUrl($scope.file, $scope)
            .then(function(result) {
                $scope.imageSrc = result;
                $scope.isLoaded = true;
            });
    };

    $scope.retrieveFaceGetFile = function (){
        $scope.isLoaded = false;
        fileReader.readAsDataUrl($scope.file, $scope)
            .then(function(result) {
                $scope.retrieveFaceImageSrc = result;
                $scope.isLoaded = true;
            });
    }

    $scope.findSimilar = function(){
        if(!$scope.file){
            alert("Select image");
            return;
        }

        var formData = new FormData();
        formData.append("file", $scope.file);
        $http.post(host + '/request/findSimilar', formData, {
            transformRequest: function (data, headersGetterFunction) {
                return data;
            },
            headers: {'Content-Type': undefined},
            accepts: "application/json; charset=utf-8"
        }).success(function (data, status) {
            $scope.similarImages = data;
        }).error(function (data, status) {
            alert("Error");
        });
    };

    $scope.retrieveFace = function(){
        var formData = new FormData();
        formData.append("file", $scope.file);
        $http.post(host + '/request/retrieveFace', formData, {
            transformRequest: function (data, headersGetterFunction) {
                return data;
            },
            headers: {'Content-Type': undefined},
            accepts: "application/json; charset=utf-8"
        }).success(function (data, status) {
            $scope.retrieveFaceOutputImageUrl1 = data[0];
            $scope.retrieveFaceOutputImageUrl2 = data[1];
        }).error(function (data, status) {
            alert("Error");
        });
    };



    //$http.get(host + '/request/add').success(function (data, status) {
    //    $scope.texData = data;
    //}).error(function (data, status) {
    //    //alert("Error ... " + status);
    //});

    $scope.findSimilarImagesClick = function(){
        $scope.findSimilarImages = true;
    };

    $scope.retrieveFaceClick = function(){
        $scope.findSimilarImages = false;
    };
});

app.directive("ngFileSelect",function(){

    return {
        link: function($scope,el){

            el.bind("change", function(e){

                $scope.file = (e.srcElement || e.target).files[0];
                if($scope.findSimilarImages) {
                    $scope.getFile();
                } else {
                    $scope.retrieveFaceGetFile();
                }
            })

        }

    }


})
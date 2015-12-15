app.controller("MyController", function ($scope, $http, $location, $rootScope, $cookieStore) {
    $scope.txtData = "";
    $scope.txtFile;
    $scope.texData = "";

    $http.get('/rest/add').success(function (data, status) {
        $scope.texData = data;
    }).error(function (data, status) {
        //alert("Error ... " + status);
    });


    $scope.onFileSelect = function($files) {
        for (var i = 0; i < $files.length; i++) {
            var file = $files[i];
            $scope.txtFile = file;
            var r = new FileReader();
            r.onload = function (e) {
                var contents = e.target.result;
                $scope.txtData = contents;
                $scope.$apply();

            };
            r.readAsText(file);
        };

        $scope.convert = function () {
            var formData = new FormData();
            formData.append("file", $scope.txtFile);
            $http.post(host + '/request/parseFile', formData, {
                transformRequest: function (data, headersGetterFunction) {
                    return data;
                },
                headers: {'Content-Type': undefined}
            }).success(function (data, status) {
                $scope.texData = data;
            }).error(function (data, status) {
                alert("Error ... " + status);
            });
        };

        $scope.download = function () {
            var element = document.createElement('a');
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent($scope.texData));
            element.setAttribute('download', "output.tex");

            element.style.display = 'none';
            document.body.appendChild(element);

            element.click();

            document.body.removeChild(element);
        };
    }
});
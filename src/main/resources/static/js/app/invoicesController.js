
'use strict';

app.controller("planetaConcienciaController",
    ['planetaConcienciaService', '$scope', function(planetaConcienciaService, $scope){

    //an array of files selected
    $scope.files = [];

    //listen for the file selected event
    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () {
            //add the file object to the scope's files collection
            $scope.files.push(args.file);
        });
    });

    $scope.invoiceMode = function(){
        alert($scope.invoiceModeValue);
        console.log($scope.invoiceModeValue);
    }

    $scope.submitCompra = function(compra){
        console.log(compra);
    }

}]).directive('fileUpload', function () {
       return {
           scope: true,        //create a new scope
           link: function (scope, el, attrs) {
               el.bind('change', function (event) {
                   var files = event.target.files;
                   //iterate files since 'multiple' may be specified on the element
                   for (var i = 0;i<files.length;i++) {
                       //emit event upward
                       scope.$emit("fileSelected", { file: files[i] });
                   }
               });
           }
       };
});


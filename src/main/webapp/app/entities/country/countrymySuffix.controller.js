(function() {
    'use strict';

    angular
        .module('trainingApp')
        .controller('CountryMySuffixController', CountryMySuffixController);

    CountryMySuffixController.$inject = ['$scope', '$state', 'Country'];

    function CountryMySuffixController ($scope, $state, Country) {
        var vm = this;
        
        vm.countries = [];

        loadAll();

        function loadAll() {
            Country.query(function(result) {
                vm.countries = result;
            });
        }
    }
})();

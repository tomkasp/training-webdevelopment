(function() {
    'use strict';

    angular
        .module('trainingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('days-offmySuffix', {
            parent: 'entity',
            url: '/days-offmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DaysOffs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/days-off/days-offsmySuffix.html',
                    controller: 'DaysOffMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('days-offmySuffix-detail', {
            parent: 'entity',
            url: '/days-offmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DaysOff'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/days-off/days-offmySuffix-detail.html',
                    controller: 'DaysOffMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DaysOff', function($stateParams, DaysOff) {
                    return DaysOff.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'days-offmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('days-offmySuffix-detail.edit', {
            parent: 'days-offmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/days-off/days-offmySuffix-dialog.html',
                    controller: 'DaysOffMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DaysOff', function(DaysOff) {
                            return DaysOff.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('days-offmySuffix.new', {
            parent: 'days-offmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/days-off/days-offmySuffix-dialog.html',
                    controller: 'DaysOffMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dayOffDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('days-offmySuffix', null, { reload: 'days-offmySuffix' });
                }, function() {
                    $state.go('days-offmySuffix');
                });
            }]
        })
        .state('days-offmySuffix.edit', {
            parent: 'days-offmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/days-off/days-offmySuffix-dialog.html',
                    controller: 'DaysOffMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DaysOff', function(DaysOff) {
                            return DaysOff.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('days-offmySuffix', null, { reload: 'days-offmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('days-offmySuffix.delete', {
            parent: 'days-offmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/days-off/days-offmySuffix-delete-dialog.html',
                    controller: 'DaysOffMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DaysOff', function(DaysOff) {
                            return DaysOff.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('days-offmySuffix', null, { reload: 'days-offmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

'use strict';

app.factory('planetaConcienciaService',
    ['$http', '$q',
        function ($http, $q) {

            var factory = {
//                getSchools: getSchools,
//                insertSchool: insertSchool,
//                updateSchool: updateSchool,
//                prepareSAMLObject: prepareSAMLObject,
//                prepareLDAPObject: prepareLDAPObject,
//                prepareCASObject: prepareCASObject
            };

            return factory;

            function insertSchool(school, authType) {
                console.log('Creating School');
                var deferred = $q.defer();

                var conf = new Config();

                console.log("Insert institution URL: " + conf.insertInstURL);

                school = prepareInstitution(school, authType);

                $http.post(conf.insertInstURL, school)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                       console.error('Error while creating School : ' + errResponse);
                       alert('There was an error creating the institution.');
                       deferred.reject(errResponse);
                    }
                );
                console.log(school);
                return deferred.promise;
            }

            function getSchools() {
                console.log('Fetching all schools');
                var deferred = $q.defer();

                var conf = new Config();

                $http.get(conf.getInstURL)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                       console.error('Error getting schools' + errResponse);
                       alert('There was an error getting the institutions.');
                       deferred.reject(errResponse);
                    }
                );

                return deferred.promise;
            }

            /**
             *  Submits the form and calls the edit endpoint
             */
            function updateSchool(school, authType) {
                console.log('Updating School with id ' + school.id);
                var deferred = $q.defer();

                var conf = new Config();

                school = prepareInstitution(school, authType);

                $http.put(conf.updateInstURL, school)
                    .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating School with id :'+ school.id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }


        }
    ]);
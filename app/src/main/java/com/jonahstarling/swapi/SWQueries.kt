package com.jonahstarling.swapi

import SWFilmsQuery
import SWPeopleQuery
import SWPlanetsQuery
import SWSpeciesQuery
import SWStarshipsQuery
import SWVehiclesQuery
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.jonahstarling.swapi.swobjects.*
import fragment.*

/**
 * Created by Jonah on 9/28/17.
 */

class SWQueries {

    fun callAllQuery(apolloClient: ApolloClient, mainListItems: MutableList<MainListObjects.MLObject>) {
        callFilmQuery(apolloClient, mainListItems)
        callPeopleQuery(apolloClient)
        callPlanetsQuery(apolloClient)
        callSpeciesQuery(apolloClient)
        callStarshipsQuery(apolloClient)
        callVehiclesQuery(apolloClient)
    }

    fun callFilmQuery(apolloClient: ApolloClient, mainListItems: MutableList<MainListObjects.MLObject>) {
        val filmQuery = SWFilmsQuery.builder().build()
        if (Films.FILMS.size < 1) {
            val filmCall = apolloClient.query(filmQuery)
            filmCall.enqueue(object : ApolloCall.Callback<SWFilmsQuery.Data>() {
                override fun onResponse(response: Response<SWFilmsQuery.Data>) {
                    val data = response.data()
                    val allFilms = data?.allFilms()
                    val films = allFilms?.films()
                    var i = 0
                    if (films != null) {
                        while (i < films.size) {
                            val filmDetails = films[i]?.fragments()?.filmDetails() as FilmDetails
                            val newFilm = Films.Film(filmDetails)
                            Films.addFilm(newFilm)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newFilm.id, newFilm.title, "Film"))
                            mainListItems.add(MainListObjects.MLObject(newFilm.id, newFilm.title, "Film"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-FILM_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callPeopleQuery(apolloClient: ApolloClient) {
        val peopleQuery = SWPeopleQuery.builder().build()
        if (People.PEOPLE.size < 1) {
            val peopleCall = apolloClient.query(peopleQuery)
            peopleCall.enqueue(object : ApolloCall.Callback<SWPeopleQuery.Data>() {
                override fun onResponse(response: Response<SWPeopleQuery.Data>) {
                    val data = response.data()
                    val allPeople = data?.allPeople()
                    val people = allPeople?.people()
                    var i = 0
                    if (people != null) {
                        while (i < people.size) {
                            val personDetails = people[i]?.fragments()?.personDetails() as PersonDetails
                            val newPerson = People.Person(personDetails)
                            People.addPerson(newPerson)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newPerson.id, newPerson.name, "Person"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-PEOPLE_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callPlanetsQuery(apolloClient: ApolloClient) {
        val planetsQuery = SWPlanetsQuery.builder().build()
        if (Planets.PLANETS.size < 1) {
            val planetsCall = apolloClient.query(planetsQuery)
            planetsCall.enqueue(object : ApolloCall.Callback<SWPlanetsQuery.Data>() {
                override fun onResponse(response: Response<SWPlanetsQuery.Data>) {
                    val data = response.data()
                    val allPlanets = data?.allPlanets()
                    val planets = allPlanets?.planets()
                    var i = 0
                    if (planets != null) {
                        while (i < planets.size) {
                            val planetDetails = planets[i]?.fragments()?.planetDetails() as PlanetDetails
                            val newPlanet = Planets.Planet(planetDetails)
                            Planets.addPlanet(newPlanet)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newPlanet.id, newPlanet.name, "Planet"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-PLANETS_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callSpeciesQuery(apolloClient: ApolloClient) {
        val speciesQuery = SWSpeciesQuery.builder().build()
        if (Species.SPECIES.size < 1) {
            val speciesCall = apolloClient.query(speciesQuery)
            speciesCall.enqueue(object : ApolloCall.Callback<SWSpeciesQuery.Data>() {
                override fun onResponse(response: Response<SWSpeciesQuery.Data>) {
                    val data = response.data()
                    val allSpecies = data?.allSpecies()
                    val species = allSpecies?.species()
                    var i = 0
                    if (species != null) {
                        while (i < species.size) {
                            val speciesDetails = species[i]?.fragments()?.speciesDetails() as SpeciesDetails
                            val newSpecies = Species.SPObject(speciesDetails)
                            Species.addSpecies(newSpecies)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newSpecies.id, newSpecies.name, "Species"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-SPECIES_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callStarshipsQuery(apolloClient: ApolloClient) {
        val starshipsQuery = SWStarshipsQuery.builder().build()
        if (Starships.STARSHIPS.size < 1) {
            val starshipsCall = apolloClient.query(starshipsQuery)
            starshipsCall.enqueue(object : ApolloCall.Callback<SWStarshipsQuery.Data>() {
                override fun onResponse(response: Response<SWStarshipsQuery.Data>) {
                    val data = response.data()
                    val allStarships = data?.allStarships()
                    val starships = allStarships?.starships()
                    var i = 0
                    if (starships != null) {
                        while (i < starships.size) {
                            val starshipDetails = starships[i]?.fragments()?.starshipDetails() as StarshipDetails
                            val newStarship = Starships.Starship(starshipDetails)
                            Starships.addStarship(newStarship)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newStarship.id, newStarship.name, "Starship"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-STARSHIPS_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

    fun callVehiclesQuery(apolloClient: ApolloClient) {
        val vehiclesQuery = SWVehiclesQuery.builder().build()
        if (Vehicles.VEHICLES.size < 1) {
            val vehiclesCall = apolloClient.query(vehiclesQuery)
            vehiclesCall.enqueue(object : ApolloCall.Callback<SWVehiclesQuery.Data>() {
                override fun onResponse(response: Response<SWVehiclesQuery.Data>) {
                    val data = response.data()
                    val allVehicles = data?.allVehicles()
                    val vehicles = allVehicles?.vehicles()
                    var i = 0
                    if (vehicles != null) {
                        while (i < vehicles.size) {
                            val vehicleDetails = vehicles[i]?.fragments()?.vehicleDetails() as VehicleDetails
                            val newVehicle = Vehicles.Vehicle(vehicleDetails)
                            Vehicles.addVehicle(newVehicle)
                            MainListObjects.addMLObject(MainListObjects.MLObject(newVehicle.id, newVehicle.name, "Vehicle"))
                            i += 1
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Log.w("SWAPI-VEHICLES_DATA", e.localizedMessage + ": " + e.cause.toString())
                }
            })
        }
    }

}
package pl.polprzewodnikowy.blog;

import org.springframework.data.repository.CrudRepository;

interface EntryRepository extends CrudRepository<Entry, Integer> {

}

from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    # CRUD operations for Animal collection in MongoDB
    
    def __init__(self, username, password):
        USER = 'aacuser'
        PASS = 'SNHU123456'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 30825
        DB = 'AAC'
        COL = 'animals'
        # Initialize Connection
        self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER, PASS, HOST, PORT))
        self.database = self.client[DB]
        self.collection = self.database[COL]
        
    # Add a new method to query for documents
    def query_documents(self, db_name, collection_name, query={}):
        try:
            # Switch to the specified database and collection
            database = self.client[db_name]
            collection = database[collection_name]
            
            # Use the find() method with the provided query
            cursor = collection.find(query)
            
            # Convert cursor results to a list
            result = list(cursor)
            
            return result
        except Exception as e:
            print(f"An error occurred while querying documents: {str(e)}")
            return []
        
    def read(self, query={}):
        try:
            # Use the find() method to read data from the collection
            cursor = self.collection.find(query)
            # Convert cursor results to a list
            result = list(cursor)
            return result
        except Exception as e:
            print(f"An error occurred while reading documents: {str(e)}")
            return []
    
    # Add an Update method
    def update_documents(self, db_name, collection_name, query={}, update_data={}):
        try:
            # Switch to the specified database and collection
            database = self.client[db_name]
            collection = database[collection_name]
            
            # Use the update_one() method with the provided query and update data
            result = collection.update_one(query, {'$set': update_data})
            
            # Return the number of objects modified
            return result.modified_count
        except Exception as e:
            print(f"An error occurred while updating documents: {str(e)}")
            return 0
    
    # Add a Delete method
    def delete_documents(self, db_name, collection_name, query={}):
        try:
            # Switch to the specified database and collection
            database = self.client[db_name]
            collection = database[collection_name]
            
            # Use the delete_many() method with the provided query
            result = collection.delete_many(query)
            
            # Return the number of objects removed
            return result.deleted_count
        except Exception as e:
            print(f"An error occurred while deleting documents: {str(e)}")
            return 0


        
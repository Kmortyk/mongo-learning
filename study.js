// start mongo process
sudo systemctl start mongod
// or reload
sudo systemctl daemon-reload
// check status
sudo systemctl status mongod
// start with os running !!!
sudo systemctl enable mongod
// stop mongo server
sudo systemctl stop mongod
// restart mongo server
sudo systemctl restart mongod
// use mongo
mongo

// start mongodb?
sudo service mongod start
// check status?
sudo service mongod status
// stop mongodb?
sudo service mongod stop
// restart mongodb
sudo service mongod restart
// start using
mongo

// current database
db
// change db
use db2
// insert data
db.inventory.insertMany([
    { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
    { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "A" },
    { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
    { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
    { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" }
 ]);

 // check mongodb path
 grep dbPath /etc/mongod.conf
 // go to data path
 cd /var/lib/mongodb/

 // insert user
 db.users.insert({user:'Tom'})
// select data
db.users.find()
// count
db.users.count()
// function id
db.users.count
// help
db.help()
// udpate document
db.users.update({user:'Tom'}, {$set:{country:'Russia'}})
// remove
db.users.remove({user: 'Tom', country:'Russia'})
// unset
db.users.update({user:'Tom'},{$unset:{country:1}})
// insert document
doc = {user:'Smith',
        favorities: {
            cities:['Chickago', 'London'],
            movies:['Matrix', '1+1']
        }}

db.users.insert(doc)
db.users.find({'favorities.cities': 'London'})
db.users.update({'favorities.movies':'1+1'},{$addToSet:{favorities.movies:'2+1'}},false,true)

db.users.find()
db.users.insert({username:'jones'})
db.users.save({username:'Smith'})
db.users.find({username:'jones'})
db.users.update({username:'Smith'}, {$set:{country:'Canada'}})
db.users.update({username:'Smith'}, {$set:{favorities:{cities:['Chickago','Rome'],movies:['Matrix','The sting']}}})
db.users.update({username:'Smith'},{$unset:{county:1}})
db.users.update({username:'Jones'},{$set:{favorities:{movies:['Rocky','Winter']}}})
db.users.find({'favorities.movies':'Matrix'})
db.users.update({'favorities.movies':'Matrix'},{$addToSet:{'favorities.movies':'Matrix2'}},false,true)
db.users.remove({'favorities.cities':'Rome'})

for(i=0;i<200000;i++){db.numbers.save({num:i});}
db.numbers.count()
db.numbers.find({num:500})
db.numbers.find({num:{$gt:199995}})
db.numbers.find({num:{$gt:20,$lt:25}})
db.numbers.find({num:{$gt:199995}}).explain()
// indexes
db.numbers.ensureIndex({num:1})
/*
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}
*/
db.numbers.getIndexes()
// show
show dbs
show collections
// stats
db.stats()
db.help()
db.users.help()
db.numbers.save
//
db.users.insert({username:'Tom', age:28, languages:['english', 'german']})
document=({username:'Bill', age:32, languages:['english','french']})
db.users.insert(document)
// ???
db.createCollection('profile', {capped:true, size:500, max:150})
// 
db.users.insert({username:'Tom',age:32,languages:['english']})
db.users.find({username:'Tom'})
db.users.find({languages:'german'})
db.users.find({username:'Tom',age:32})
db.users.find({username:'Tom'},{age:1})
db.users.find({username:'Tom'},{age:true,_id:false})
db.users.insert({username:'Alex',age:28,company:{name:'Microsoft',country:'usa'}})

db.users.find().limit(3)
db.users.find().skip(2)
db.users.find().sort({username:1})
db.users.find().sort({username:1}).skip(1).limit
// ????
db.users.find().sort({$natural:-1}).limit

db.users.find({username:'Tom'},{languages:{$slice:1}})
db.users.find({username:'Tom'},{'languages':{$slice:[-1,1]}})

db.users.find({},{username:1})
db.users.find({username:'Tom'}).count()
db.users.find({}, {username:1}).count()
db.users.find({username:{$exists:1}}).count()
db.users.find({}, {_id:0, username:1})
db.users.find({username:'Tom'}).skip(2).count(true)
db.users.distinct('username')

db.profile.remove()
db.profile.drop()
db.users.find({username:'Tom'},{languages:{$slice:1}})
db.users.find({username:'Tom'},{languages:{$slice:[-1,1]}})

db.users.find({age:{$ne:28}})
db.users.find({age:{$nin:['english','french']}})
db.users.find({languages:{$all:['english','french']}})

db.users.find({$or:[{username:'Tom'},{age:28}]})
db.users.find({'username':'Eugene', $or:[{age:29},{'languages':'english'}]})

db.users.find({languages:{$size:2}})
db.users.find({company:{$exists:true}})
db.users.find({username:{$regex:'b^'}})
db.users.find({username:{$regex:'l+'}})
db.users.save({username:'Eugene',age:29,languages:['english','spanish','french']})

db.users.update({username:'Tom'},{username:'Tom',age:25,married:false},{upsert:true})
db.users.update({username:'Eugene',age:29},{$set:{age:30}})
db.users.update({username:'Tom'},{$set:{username:'Tom',age:25,married:false}},{multi:true})

db.users.update({username:'Tom'},{$inc:{salary:100}})
db.users.update({username:'Tom'},{$unset:{salary:1}})
db.users.update({username:'Tom'},{$unset:{salary:1, age:1}})
db.users.update({username:'Tom'},{$addToSet:{languages:{$each:['spanish']}}})
db.users.update({username:'Tom'},{$addToSet:{languages:{$each:['russia','italian']}}})
db.users.update({username:'Tom'},{$pop:{languages:-1}})
db.users.update({username:'Tom'},{$pull:{languages:'english'}})
// ????
db.users.update({username:'Tom'},{$pullAll:{languages:['english','french','german']}})
db.users.remove({age:{$lt:30}})
db.users.remove({username:'Tom'},true)

db.users.update({username:'Tom'},{$push:{languages:'english'}})


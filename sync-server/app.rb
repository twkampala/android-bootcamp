require 'sinatra'
require 'faker'
require 'json'
require 'ruby_identicon'

set :bind, '0.0.0.0'

fake_items = (1..20).map do |i|
  id = SecureRandom.uuid
  [ id, { id: id, name: Faker::Name.name, city: Faker::Address.city, company: Faker::Company.name, email: Faker::Internet.email, phone: Faker::PhoneNumber.cell_phone, description: Faker::Hacker.say_something_smart } ]
end
items = Hash[*fake_items.flatten]

get '/items/?' do
  sleep 0.2
  content_type :json
  { ids: items.keys }.to_json
end

put '/items/:id/?' do
  sleep 0.2
  content_type :json
  items[params[:id]] = params[:item]
  200
end

get '/items/:id/?' do
  sleep 0.2
  content_type :json
  items[params[:id]].to_json
end

get '/items/:id/avatar/?' do
  content_type 'image/jpg'
  headers["Content-Disposition"] = "inline;filename=the_file.jpg"
  RubyIdenticon.create params[:id]
end

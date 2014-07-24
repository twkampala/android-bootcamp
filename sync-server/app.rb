require 'sinatra'
require 'faker'
require 'json'

items = (1..20).map do |i|
  { id: i, name: Faker::Name.name, city: Faker::Address.city, company: Faker::Company.name, email: Faker::Internet.email, phone: Faker::PhoneNumber.cell_phone, description: Faker::Hacker.say_something_smart }
end

get '/items' do
  content_type :json
  { items: items }.to_json
end

post '/items' do
  content_type :json
  items << params[:item]
end

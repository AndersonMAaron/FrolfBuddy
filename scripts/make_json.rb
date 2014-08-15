require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require 'json'
require 'pp'

Capybara.run_server = false
Capybara.current_driver = :selenium
#prodigy
#innova
Capybara.app_host = 'http://infinitediscs.com/category/innova'

module Disc
  class Reader
    include Capybara::DSL

    def make_json
      visit('/')
      num_discs = page.all(:xpath, '//a[@class="more"]').size

      for i in 0..(num_discs-1)
      	puts i
      	sleep 1

      	rim_depth = nil
      	rim_width = nil

		page.all(:xpath, '//a[@class="more"]')[i].click

		name = page.find(:xpath, '//h1[@class="title"]').text

      	diametercheck = page.all(:xpath, '//div[@class="two_third last"]//ul//li[contains(., "Diameter")]')
      	if diametercheck.empty?
      		diameter = ""
      	else
      		diameter = diametercheck[0].text
      	end
      	heightcheck = page.all(:xpath, '//div[@class="two_third last"]//ul//li[contains(., "Height")]')
      	if heightcheck.empty?
      		height = ""
      	else
      		height = heightcheck[0].text
      	end
      	rimcheck = page.all(:xpath, '//div[@class="two_third last"]//ul//li[contains(., "Rim Thickness")]')
      	if rimcheck.empty?
      		rimcheck2 = page.all(:xpath, '//div[@class="two_third last"]//ul//li[contains(., "Rim Depth")]')
      		if rimcheck2.empty?
      			rim_depth = 0
      			rim_width = 0
      		else
      			rim_depth = rimcheck2[0].text
      			rim_width = page.find(:xpath, '//div[@class="two_third last"]//ul//li[contains(., "Rim Width")]').text
      		end
      	else
			thickness = rimcheck[0].text
		end

      	plasticcheck = page.first(:xpath, '//a[contains(@href, "http://infinitediscs.com/Plastic-Grade")]')
      	plastic = plasticcheck == nil ? "" : plasticcheck.text

      	skillcheck = page.first(:xpath, '//a[contains(@href, "http://infinitediscs.com/disc-Skill-Level")]')
      	skill = skillcheck == nil ? "" : skillcheck.text

      	flightcheck = page.first(:xpath, '//a[contains(@href, "http://infinitediscs.com/Disc-Flight")]')
      	flight = flightcheck == nil ? "" : flightcheck.text

      	stabilitycheck = page.first(:xpath, '//a[contains(@href, "http://infinitediscs.com/disc-stability")]')
      	stability = stabilitycheck == nil ? "" : stabilitycheck.text

      	typecheck = page.first(:xpath, '//a[contains(@href, "http://infinitediscs.com/Disc-Type")]')
      	type = typecheck == nil ? "" : typecheck.text

      	weightcheck = page.first(:xpath, '//div[@class="two_third last"]//ul//li[contains(., "Available Weights")]')
      	weights = weightcheck == nil ? "" : weightcheck.text

      	if rimcheck.empty?
			disc_hash = {
			    "name" => name,
			    "manufacturer" => "Discraft",
			    "type" => type,	
			    "diameter" => diameter,
			    "height" => height,
			    "rimdepth" => rim_depth,
			    "rimwidth" => rim_width,
			    "weights" => weights,
			    "plastic" => plastic,
			    "skill" => skill,
			    "flight" => flight,
			    "stability" => stability
			}
		else
			disc_hash = {
			    "name" => name,
			    "manufacturer" => "Discraft",
			    "type" => type,	
			    "diameter" => diameter,
			    "height" => height,
			    "thickness" => thickness,
			    "weights" => weights,
			    "plastic" => plastic,
			    "skill" => skill,
			    "flight" => flight,
			    "stability" => stability
			}
		end
		pp disc_hash
		to = '/Users/o607771/Documents/stash_repositories/sdlc-tooling/../../../Desktop/innova'

		filename = to + "/#{name.gsub(/\s+/, "_").downcase}.json"
		File.open(filename,"w") do |f|
		  f.write(disc_hash.to_json)
		end

      	page.go_back
      end
    end
  end
end

t = Disc::Reader.new
t.make_json
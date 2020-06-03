--redis.log(redis.LOG_NOTICE,'ARGV[1] is '..ARGV[1])

local current = redis.call('GET', KEYS[1])
if current == false then
    --redis.log(redis.LOG_NOTICE,KEYS[1]..' is nil ')
    return '-1'
end
local isdel = redis.call('DEL', KEYS[1])
if isdel == 1 then
     --redis.log(redis.LOG_NOTICE,' del '..KEYS[1]..' success')
     return '1';
else
     --redis.log(redis.LOG_NOTICE,'del '..KEYS[1]..' failed')
     return '0';
end


package com.example.springbootfilterheader.controller;

import com.example.springbootfilterheader.annotation.HeaderChecker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping
public class CacheController {
    private Map<String, Long> data = new HashMap<>();

    @GetMapping("cache/{id}")
    @HeaderChecker(headerNames = "token")
//    @HttpCacheControl(key = "#id", value = "max-age=10, public")
    public Long get(@PathVariable String id) {

        return data.computeIfAbsent(id, s -> System.currentTimeMillis());
    }

    @PostMapping("cache/{id}")
//    @HttpETagBind(HttpETagBindkey = "#id")
    public void post(@PathVariable String id) {
        data.put(id, System.currentTimeMillis());
    }

    @GetMapping("html")
    public ResponseEntity<String> html() {
        return ResponseEntity
                .ok()
                .body("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><script src=\"https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js\"></script><script>$(document).ready(function(){$(\"button:nth-child(1)\").click(function(){$.get(\"./cache/1\",function(data,status){$(\"ol\").append(\"<li>cache1: data: \"+data+\"</li>\")})});$(\"button:nth-child(2)\").click(function(){$.get(\"./cache/2\",function(data,status){$(\"ol\").append(\"<li>cache2: data: \"+data+\"</li>\")})});$(\"button:nth-child(3)\").click(function(){$.post(\"./cache/1\",function(data,status){$(\"ol\").append(\"<li>cache1: modify cache1</li>\")})});$(\"button:nth-child(4)\").click(function(){$.post(\"./cache/2\",function(data,status){$(\"ol\").append(\"<li>cache2: modify cache2</li>\")})})});</script></head><body><button>get cache1</button><button>get cache2</button><button>modify cache1</button><button>modify cache2</button><ol><li>start test</li></ol></body></html>");
    }
}
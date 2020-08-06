package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.CommenError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.access.InvalidInvocationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private final List<RsEvent> rsList = init();

  private List<RsEvent> init() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无分类",
            new User("A",18,"male","A@qq.com","11234567890")));
    rsEvents.add(new RsEvent("第二条事件", "无分类",
            new User("B",28,"female","B@qq.com","11234567890")));
    rsEvents.add(new RsEvent("第三条事件", "无分类",
            new User("C",38,"male","C@qq.com","11234567890")));
    return rsEvents;
  }

  @GetMapping("/rs/list/{index}")
  public ResponseEntity getOneRsEvent(@PathVariable int index) {
    if(index>rsList.size()){

      throw new InvalidInvocationException("invalid index");
    }
    return ResponseEntity.ok(rsList.get(index - 1));
  }

  @GetMapping("/rs/list")
  public ResponseEntity<List<RsEvent>> getRsEvent(@RequestParam(required = false) Integer start,
                                  @RequestParam(required = false) Integer end) {
    if (start == null || end == null) {
      return ResponseEntity.ok(rsList);
    }
    return ResponseEntity.ok(rsList.subList(start - 1, end));
  }

  @PostMapping("/rs/add")
  public ResponseEntity addRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
    for(User user:UserController.users){
      if(rsEvent.getUser().getUserName().equals(user.getUserName())){
        return ResponseEntity.created(null).build();
      }
    }
    UserController.users.add(rsEvent.getUser());
    return ResponseEntity.created(null).build();
  }

  @PostMapping("/rs/update/{index}")
  ResponseEntity updateRsEvent(@PathVariable int index, @RequestBody @Valid RsEvent rsEvent) {
    if(rsEvent.getEventName()!=null) rsList.get(index - 1).setEventName(rsEvent.getEventName());
    if (rsEvent.getKeyword() != null) rsList.get(index - 1).setKeyword(rsEvent.getKeyword());
    return ResponseEntity.created(null).body(index);
  }

  @PostMapping("/rs/delete/{index}")
  ResponseEntity deleteRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
    return ResponseEntity.created(null).body(index);
  }

}
package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@Autowired
RsEventRepository rsEventRepository;
@Autowired
UserRepository userRepository;
  private List<RsEvent> init() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无分类",0));
    rsEvents.add(new RsEvent("第二条事件", "无分类",0));
    rsEvents.add(new RsEvent("第三条事件", "无分类",0));

    return rsEvents;
  }

  @GetMapping("/rs/list/{index}")
  public ResponseEntity getOneRsEvent(@PathVariable int index) throws InvalidIndexException {
    if(index < 1 || index > rsList.size()){
      throw new InvalidIndexException("invalid index");
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
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent){
    if(!userRepository.findById(rsEvent.getUserId()).isPresent()){
      return ResponseEntity.badRequest().build();
    }
    RsEventEntity rsEventEntity= RsEventEntity.builder().userId(rsEvent.getUserId()).keyword(rsEvent.getKeyword()).
            eventName(rsEvent.getEventName()).build();
rsEventRepository.save(rsEventEntity);
    return ResponseEntity.created(null).build();
  }

  @PostMapping("/rs/{index}/update")
  ResponseEntity updateRsEvent(@PathVariable int index, @RequestBody @Valid RsEvent rsEvent) {
    if(rsEvent.getEventName()!=null) rsList.get(index - 1).setEventName(rsEvent.getEventName());
    if (rsEvent.getKeyword() != null) rsList.get(index - 1).setKeyword(rsEvent.getKeyword());
    return ResponseEntity.created(null).body(index);
  }

  @PostMapping("/rs/{index}/delete")
  ResponseEntity deleteRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
    return ResponseEntity.created(null).body(index);
  }

  @ExceptionHandler({InvalidIndexException.class, MethodArgumentNotValidException.class})
  public ResponseEntity exceptionHandler(Exception ex){
    String errorMessage;
    CommenError commError =new CommenError();
    if(ex instanceof MethodArgumentNotValidException){
      errorMessage="invalid param";
    }else {
      errorMessage=ex.getMessage();
    }
    commError.setError(errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commError);
  }

}
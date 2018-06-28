package com.github.hualuomoli.sample.plugin.mq.receiver.dealer.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.mq.receiver.MessageDealer;
import com.github.hualuomoli.sample.plugin.mq.receiver.anno.Anno;
import com.github.hualuomoli.sample.plugin.mq.receiver.dealer.service.ShowService;

@Service(value = "com.github.hualuomoli.sample.plugin.mq.receiver.dealer.queue.QueueMessageDealer")
@Anno(destinationName = "sample_queue")
public class QueueMessageDealer implements MessageDealer {

  @Autowired
  private ShowService showService;

  @Override
  public void onMessage(String data) {
    showService.show(QueueMessageDealer.class, data);
  }

}

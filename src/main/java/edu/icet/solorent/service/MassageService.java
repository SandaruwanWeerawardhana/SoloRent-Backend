package edu.icet.solorent.service;

import edu.icet.solorent.dto.Massage;
import org.aspectj.bridge.Message;

import java.util.List;

public interface MassageService {
    void add(Massage message);

    void delete(Long id);

    List<Massage> getAll();
}

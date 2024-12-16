package com.profit.ProFit.Repository;

import com.profit.ProFit.Model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepositoty extends JpaRepository<Community, Integer> {

    Community save(Community community);

}

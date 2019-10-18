package sam.biblio.batch.notification;

import sam.biblio.model.library.Member;

import java.util.function.Consumer;

public interface MemberNotifier extends Consumer<Member> {
}

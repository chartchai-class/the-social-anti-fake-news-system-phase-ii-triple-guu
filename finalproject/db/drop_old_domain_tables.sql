-- WARNING: This script will DROP old domain tables if they exist.
-- It is intended to remove previous event/organizer/participant data
-- after the backend was renovated to the news/comment/vote domain.
--
-- IMPORTANT: This is irreversible. Back up your database before running.

SET FOREIGN_KEY_CHECKS=0;

-- Old domain tables (try multiple name variants)
DROP TABLE IF EXISTS `event_participants`;
DROP TABLE IF EXISTS `event_participant`;
DROP TABLE IF EXISTS `event_organizer`;
DROP TABLE IF EXISTS `event_organizers`;
DROP TABLE IF EXISTS `organizer_events`;

DROP TABLE IF EXISTS `event`;
DROP TABLE IF EXISTS `events`;

DROP TABLE IF EXISTS `organizer`;
DROP TABLE IF EXISTS `organizers`;

DROP TABLE IF EXISTS `participant`;
DROP TABLE IF EXISTS `participants`;
DROP TABLE IF EXISTS `participant_event_history`;

-- If your schema used different names, add them above before running.

SET FOREIGN_KEY_CHECKS=1;

-- End of script

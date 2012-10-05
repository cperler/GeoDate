GeoDate is an idea I came up with an 2008 when the Android OS was in beta.  It was location-based mobile match-making, an idea that back then was untouched (match.com had no mobile presence, for example), and still is something being chased around.

This repo contains the client code I'd started building for this project (server and db owned by others).

**

Proposal

Develop a location-based mobile matchmaking application for the Google phone.
 

Concept, Briefly

    Each user would have an associated profile describing basic attributes about themselves as well as the types of people they'd be interested in meeting. 
    Via a background process, users could opt to enable tracking whereby the phone would periodically inform a central server of its location. 
    Based on a current location and user-defined radius, users could display a map visualizing where nearby people of interest are located and then provide access to those found profiles.

Justification

Mobile phone applications are in an uptrend: they have not yet reached market saturation and even in already competitive genres, there is still room for new development. 

 

There are a number of social networking mobile applications available across the variety of smartphone platforms.  The goal of these existing mobile applications is to emulate Facebook and/or Myspace while providing additional location-based enhancements such as map-based relationship visualizations as well as facilitated messaging.

 

The existing set of mobile networking applications allow users to establish a roster of mobile friends, and then see where they are, what they're doing, and vice versa.  Friends may be added in a number of ways depending on the application, the two primary methods being that of importing an existing social network (or accessing one directly via web), or inviting via text messages, phone calls, and/or emails.  The point of note here is these applications rely on users having a pre-existing base of friends.

 

A mobile matchmaking application fits a different niche: a preexisting base of friends is not required and a network of friends is not maintained.  Instead, a matchmaking application simply eases the work in locating people of interest.  As far as I'm aware, no mobile application has yet filled this gap.

 

Technology

    The Google Android platform provides a Java framework and emulator for mobile application development.
    Interfaces may be coded in XML and databases may be maintained in SqlLite. 
    The initial T-Mobile phone, the G1, has a built-in GPS, needed for assessing one's current location. 
    The Android platform in conjunction with the G1 provides background service capabilities, which can be used to periodically inform a central server of one's current location without extra user involvement.
    A central webserver would be required as a repository of active users and their locations as well as provide the actual people/location-matching methods. 

Points of Concern

    Secure access to the webserver is critical.  In the wrong hands, the ability to locate people of interest is known as stalking: in the least, there must be methods of authorization and authentication, and there needs to be some guarantee of privacy and security.
    Expecting the worst, the legal obligations need to be understood should the application be used to facilitate a crime.
    Periodically invoking the GPS from a background process has already been demonstrated to be a battery hog.  To address, preferences could be made available that automatically disabled the tracking after some duration or as of some time.
    Marketing a matchmaking application is something of a catch-22: until there's a large user base, the application is worthless.  That said, successful marketing is not an impossibility given there are existing monetized web applications with large user bases.
    Separate web-based registration may be necessary in order to monetize access.
    Should the application take off, scaling would be required in multiple areas, such as database optimization (on the webserver), and potentially development across additional phone platforms.

Future Enhancements

Once a successful application is made available, there's a limitless list of possible enhancements that could improve the application's usefulness.  Some examples…

    The G1 could create ad hoc networks with other G1 phones, and could then be used to alert users, should they opt as such, that their persons of interest are within range.
    Rating systems or peer reviews could be used for tagging profiles.
    Smarter location searching could report named locations (such as the name of a bar) in addition to displayed locations (defined by latitude and longitude).
    The service industry could advertise as locations of interest for specific types of people. 

Current Status

Proof of concept has been successfully demonstrated.  I've developed a demo application on the Android emulator that:

    Creates a local application-specific SqlLite database if necessary.
    Allows users to create and save a basic profile (gender and age).
    Provides a preference for turning on a background tracking process, which periodically pings a PHP/MySql webservice with a simulated location.
    Allows users to define and store the types of people of interest (gender, age range and allowable radius of distance).
    Provides a Google map view upon which users can visualize (via pushpins) where their people of interest are located.  The view calls an exposed webservice method which does a location-based search across the set of stored latitude/longitude pairs.

Next Steps

With a proof of concept realized, the next step is to redesign the implementation with the goal of achieving a production-capable model.  A production-capable model is one that fulfills the basic goals while addressing the needs for security, eventual monetization, and scaling.  Once designed, development can commence on both the client and server components.  Server development would of course require access to a webserver that provides database support.  Following that (or in tandem), a graphically appealing and easy-to-navigate UI would need to be constructed.  Due to the nature of Android, the UI can be plugged in at a later stage of development with minimal overhead.  Finally, a website for the application should be pieced together for purposes of marketing, and registration if necessary.

 

A Few References

http://www.techcrunch.com/2008/09/28/the-state-of-location-based-social-networking-on-the-iphone/

http://www.techcrunch.com/2007/12/06/limejuices-mobile-social-network-its-easy-and-so-people-may-use-it/

http://code.google.com/android/

http://www.androidapps.com/reviews.php

http://www.148apps.com/top-148-iphone-os-apps-ever/

 
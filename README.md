The Ergo Engine
=================================================
Sections
=================================================
Status

About

Features

Building

=================================================
Status
=================================================
This project is still extremely early. There is a ton of room for improvement and I'm positive there are many things I could and probably should have done differently. However, I feel that the project does have potential and I would love to see it grow and be used by the indie game development community. There are probably a ton of better solutions to use out there, but my hope is that we as a community, can make this into something awesome!

There is currently Javadoc provided with the project, but within the next several days I plan on creating a wiki and adding some examples for how to do various things with the engine, such as create objects in javascript, send messages, use the components and sub systems, extend the game world, etc.

=================================================
About
=================================================
This project started out being developed along side of a game project I was working on which soon fizzled out due to lack of help creating the various assets. Although I am still actively developing the engine, I've decided to make it open source in hopes that some of you may be able to use it for game projects of your own. Below is a list of the major features that may interest you. If anything, i hope this can serve as a learning project.

=================================================
Features
=================================================
**Composite Game Object Design**

This is by far the highlight of the project. Instead of using the traditional deep inheritance hierarchies found in most game engines, I decided to use a composite approach where almost any object imaginable, can be a direct instance of the GameObject class. Objects are defined by the components you assign to them. There are however some cases where a component may require a specific interface. In these cases, I extend the GameObject class to implement the interface. Weapons in my game for example, are instances of BaseWeapon which extends the Weapon interface. Both melee and ranged weapons are now always direct instances of BaseWeapon. I decided to still use some inheritance where logical vs being a composite design purest.

**JavaScript Scripting Support**

For this I used the Mozilla Rhino Engine because a: it ships standard with the Java JDK and b: it's simple to use but fast. I'll soon be adding some examples to the wiki for how to create scripts that can be used to define game objects.

**Box2D**

Well, I didn't do a whole lot here. I built a small wrapper ontop of LibGDX's wrapper, which works in conjunction with PhysicsComponents. I'll be posting examples on how to use work with this system soon.

**There is stilla ton to come!**

=================================================
Building
=================================================
The project was developed in Eclipse and comes with what should be a ready-to-use Eclipse project file. In addition, the project is built on top of LibGDX. The pre-built libraries are located within the libs directory. Be sure to add them to the projects build path if you decide not to use the Eclipse project files. Keep in mind that this is not intended to be a stand alone project. In order to use it, you'll need to create a new Java or Android project, and then link the project + the required libGDX libraries in order to do anything with it. Again, I'll be posting sample code/projects soon.
